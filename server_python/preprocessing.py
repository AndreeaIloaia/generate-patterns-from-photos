import math
import numpy as np
import matplotlib.pyplot as plt
import csv

"""
primul criteriu verifica poalele fustei daca formeaza volane sau nu (tipic closului)
"""


def criteriu_1(vertices, edges, seams_init):
    # seams = get_points_trigonometrical_order(vertices)
    seams = sort_triag(vertices, seams_init)

    list_category_1 = []
    list_category_2 = []
    sum_y_1 = 0
    sum_y_2 = 0
    for index in range(0, len(seams) - len(seams) % 2, 2):
        list_category_1.append(seams[index])
        sum_y_1 += seams[index][1]
        list_category_2.append(seams[index + 1])
        sum_y_2 += seams[index + 1][1]

    y_mean_category_1 = sum_y_1 / len(list_category_1)
    y_mean_category_2 = sum_y_2 / len(list_category_2)

    difference = abs(y_mean_category_1 - y_mean_category_2)
    return difference


"""
Criteriul 4 calculeaza distanta cantului rochiei la poale si proiectia punctului unde vine soldul pe oX
daca distanta e foarte mare => tip bie sau poate fi clos
daca distanta e aproape de 0 => tip drept
vom lua ca reper punctul x(70, 133,-15)
daca da numar negativ => conica
"""


def criteriu_4(vertices, edges, seams):
    seam = get_lowest_points(vertices)
    sorted_after_x = seam.copy()
    sorted_after_x.sort(key=lambda p: p[0], reverse=True)
    # punctul cel mai din dreapta al tivului rochiei
    point = sorted_after_x[0][0]
    x = [70.0, 133.0, -15.0]

    dif = point - float(70)
    return dif


"""
Criteriul 5 calculeaza numarul de puncte ajutatoare pe cusaturile care leaga talia de poale
conventia este ca o linie e formata din 4 puncte, 2 in capete si 2 pe mijloc, iar noi vom numara numai punctele de mijloc
pentru o rochie cu fusta dreapta sau clos, avem 1 sau 2 cusaturi, in timp ce pentru o fusta in clini, vom avea cel putin 6 cusaturi
(fusta in 6 clini)
daca numarul de puncte <= 4 => fusta dreapta
daca identifica clos (are volane la poale) => -1
altfel => fusta in clini
"""


def criteriu_5(vertices, edges, seams):
    seam = get_lowest_seam(vertices, seams)
    # seam = sort_triag(vertices)

    waist_seam_vertices = []
    # point = [x, y, z]
    for point in vertices:
        if point[1] > 210 and point[1] < 230:
            waist_seam_vertices.append(point)

    # waist_seam_vertices = get_no_repetitive_poits(waist_seam_vertices)

    # cautam cel mai jos punct din talie si cel mai inalt din poale si calculam numarul de puncte dintre cele 2 pe oY
    sorted_lower = waist_seam_vertices.copy()
    sorted_lower.sort(key=lambda p: p[1], reverse=False)
    min_point = sorted_lower[0]
    sorted_hip = seam.copy()
    sorted_hip.sort(key=lambda p: p[1], reverse=True)
    max_point = sorted_hip[0]

    no_vertices = 0
    vert = []
    for i in vertices:
        if get_error_between_points(i, max_point) is False and abs(i[1] - max_point[1]) > 2:
            if i[1] < min_point[1] and i[1] > max_point[1]:
                no_vertices += 1
                vert.append(i)

    return no_vertices


"""
doar pentru cusatura de la poalele modelului
"""


def get_seams(vertices, min_global, seams):
    a = []
    for point in min_global:
        index = vertices.index(point)
        i = 0
        for seam in seams:
            if index in seam:
                a.append(i)
            i += 1

    return a

"""
Ordonam dupa y si luam primele 6 cele mai mici valori care trebuie sa apartina de 2 cusaturi

"""
def get_lowest_seam(vertices, seams):
    aux_list = vertices.copy()
    # aux_list = get_no_repetitive_poits(aux_list)
    aux_list.sort(key=lambda p: p[1])

    out = False
    index_seams = []
    no_seams = 0

    for i in range(6, len(vertices)):
        min_global = aux_list[:i]

        # cautam indicii celor 2 cusaturi din care fac parte
        a = get_seams(vertices, min_global, seams)

        for index in set(a):
            if a.count(index) > 1 and index not in index_seams:
                no_seams += 1
                index_seams.append(index)
            if no_seams == 2:
                out = True
                break
        if out:
            break


    the_two_seams = seams[index_seams[0]] + seams[index_seams[1]]
    lowest_points = []
    for t in the_two_seams:
        lowest_points.append(vertices[t])

    lowest_points = get_no_repetitive_poits(lowest_points)
    return lowest_points



def get_no_repetitive_poits(seam):
    aux_list = []
    seam.sort(key=lambda p: p[0])
    for index in range(0, len(seam) - 1):
        if get_error_between_points(seam[index], seam[index + 1]) is False:
            aux_list.append(seam[index])
        if index == len(seam) - 2:
            aux_list.append(seam[-1])
    return aux_list


def sort_triag(vertices, seams):
    lower_seam_vertices = get_lowest_seam(vertices, seams)
    lower_seam_vertices = get_no_repetitive_poits(lower_seam_vertices)
    x = []
    y = []
    for p in lower_seam_vertices:
        x.append(p[0])
        y.append(p[2])

    x0 = np.mean(x)
    y0 = np.mean(y)

    r = np.sqrt((x - x0) ** 2 + (y - y0) ** 2)

    angles = np.where((y - y0) > 0, np.arccos((x - x0) / r), 2 * np.pi - np.arccos((x - x0) / r))

    mask = np.argsort(angles)

    x_sorted = []
    y_sorted = []
    for i in mask:
        x_sorted.append(x[i])
        y_sorted.append(y[i])

    lower_aux = []
    for i in mask:
        lower_aux.append(lower_seam_vertices[i])

    return lower_aux


def get_lowest_points(vertices):
    lower_seam_vertices = []
    for point in vertices:
        if point[1] < 100:
            lower_seam_vertices.append(point)

    return lower_seam_vertices


def get_error_between_points(point1, point2):
    error_x = abs(point1[0] - point2[0])
    error_y = abs(point1[1] - point2[1])
    error_z = abs(point1[2] - point2[2])

    if error_x < 2 and error_y < 2 and error_z < 2:
        return True
    return False


def plot_lines(point_on_oX, point_cant, point_help):
    plt.grid()
    x1 = [point_on_oX[0], point_cant[0]]
    y1 = [point_on_oX[1], point_cant[1]]
    # plotting the line 1 points
    plt.plot(x1, y1, label="line 1")
    # line 2 points
    x2 = [point_on_oX[0], point_help[0]]
    y2 = [point_on_oX[1], point_help[1]]
    # plotting the line 2 points
    plt.plot(x2, y2, label="line 2")

    plt.xlabel('x - axis')
    plt.ylabel('y - axis')
    plt.legend()
    plt.title('plot lines', fontsize=10)

    # plt.savefig('how_to_plot_a_vector_in_matplotlib_fig1.png', bbox_inches='tight')
    plt.show()
    plt.close()


def write_in_csv(id, difference_height, difference_x, no_vertices):
    # header = ['id', 'type',  'diff_height', 'angle', 'width', 'x', 'no_vertices']
    header = ['id', 'type',  'diff_height', 'x', 'no_vertices']
    data = [id, 0, difference_height, difference_x, no_vertices]

    with open('data3.csv', "a") as file:
        writer = csv.writer(file, delimiter=',')
        writer.writerow(header)
        writer.writerow(data)
