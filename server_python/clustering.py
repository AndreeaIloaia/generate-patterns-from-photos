import csv
import numpy as np
from scipy.spatial import distance
import matplotlib.pyplot as plt
from sklearn import preprocessing
import numpy as np  # linear algebra
import pandas as pd  # data processing, CSV file I/O (e.g. pd.read_csv)
import seaborn as sns  # visualization
from sklearn.cluster import KMeans
from sklearn.metrics import accuracy_score
from sklearn.neural_network import MLPClassifier  # neural network
from sklearn.model_selection import train_test_split, GridSearchCV
from sklearn import metrics
from sklearn.metrics import silhouette_score
from sklearn.metrics import davies_bouldin_score


def read_from_csv():
    rows = []
    with open('data3.csv', newline='') as csvfile:
        reader = csv.reader(csvfile, delimiter=' ', quotechar='|')
        for row in reader:
            # id', 'type',  'diff height', 'x', 'no_vertices'
            if row == [] or row == '\n' or row == ' ':
                continue
            a = row[0].split(',')
            # prima linie
            if a[0] == 'id':
                continue
            rows.append([a[1], float(a[2]), float(a[3]), float(a[4])])

    return rows


def encode_labels(type):
    val = []
    if type == 'clini':
        val = [1, 0, 0]
    if type == 'dreapta':
        val = [0, 1, 0]
    if type == 'clos':
        val = [0, 0, 1]

    return val


def encode_labels2(type):
    if type == 'clini':
        val = 0
    if type == 'dreapta':
        val = 1
    if type == 'clos':
        val = 2

    return val


def decode_label(prediction_encoding):
    val = 'error'
    if prediction_encoding[0][0] == 1:
        val = 'clini'
    if prediction_encoding[0][1] == 1:
        val = 'dreapta'
    if prediction_encoding[0][2] == 1:
        val = 'clos'

    return val


def decode_label2(prediction_encoding):
    val = 'error'
    if prediction_encoding[0] == 0:
        val = 'clini'
    if prediction_encoding[0] == 1:
        val = 'dreapta'
    if prediction_encoding[0] == 2:
        val = 'clos'

    return val


def plot_data():
    # plot data
    data = read_from_csv()[:]
    x = []
    y = []
    z = []
    type = []
    for garment in data:
        # print(garment)
        if garment[0] == 'clos':
            type.append('yellow')
        elif garment[0] == 'clini':
            type.append('blue')
        elif garment[0] == 'dreapta':
            type.append('red')

        x.append(garment[1])
        y.append(garment[2])
        z.append(garment[3])

    np.random.seed(42)

    xs = np.array(x)
    xs = xs.astype(float)
    ys = np.array(y)
    ys = ys.astype(float)
    zs = np.array(z)
    zs = zs.astype(float)

    fig = plt.figure()
    ax = fig.add_subplot(111, projection='3d')
    ax.scatter(xs, ys, zs,
               c=type, cmap='viridis',
               edgecolor='k', s=40, alpha=0.5)

    ax.set_title("Plot cluster tipuri fuste de la rochii")
    ax.set_xlabel("delta voal poale rochie")
    ax.set_ylabel("distanta sold - cant")
    ax.set_zlabel("numar puncte pe cusaturi laterale")
    ax.dist = 10

    plt.autoscale(enable=True, axis='x', tight=True)

    plt.show()


def mlp_classifier(f1, f2, f3):
    # use mlp classifier
    data = read_from_csv()[:]
    # test = read_from_csv()[-1]
    train_x = []
    train_y = []
    for i in data:
        train_x.append([i[1], i[2], i[3]])
        train_y.append(encode_labels2(i[0]))
    test_x = []
    normalized_X = preprocessing.normalize(train_x)
    test_x = [[f1, f2, f3]]
    normalized_test = preprocessing.normalize(test_x)

    train_x = normalized_X
    test_x = normalized_test

    mlp = MLPClassifier(activation='relu',
                        hidden_layer_sizes=(3,),
                        solver='sgd',
                        alpha=1e-10,
                        learning_rate_init=0.01,
                        random_state=1, max_iter=5000)
    mlp.fit(train_x, train_y)

    pred = mlp.predict(test_x)

    loss_values = mlp.loss_curve_
    plt.plot(loss_values)
    plt.show()
    print(pred)
    return decode_label2(pred)


def load_data(file_path):
    inputs = []
    outputs = []

    with open(file_path) as csv_file:
        csv_reader = csv.reader(csv_file, delimiter=',')
        line_count = 0
        for row in csv_reader:
            if line_count == 0:
                continue
            else:
                inputs.append([float(i)
                               for i in row[2:]])
                outputs.append(row[1])

            line_count += 1

    return inputs, outputs


def k_means(f1, f2, f3):
    data = read_from_csv()[:]
    inputs, outputs = load_data('data3.csv')

    # np.random.seed(3)

    obj = KMeans(n_clusters=3, random_state=0)
    data = obj.fit(inputs)
    centroids = obj.cluster_centers_
    print('Centroids: ')
    print(centroids)

    test_xx = [[f1, f2, f3]]
    test_x = obj.predict(test_xx)
    labels = obj.fit_predict(inputs)
    # print(f'Silhouette Score(n=3): {silhouette_score(inputs, test_x)}')
    db_index = davies_bouldin_score(inputs, labels)
    db_index1 = davies_bouldin_score(inputs, labels)
    print(db_index)
    # print(db_index2)

    obj.fit(inputs)
    label = obj.predict(inputs)
    print(silhouette_score(inputs, label))
    # sns.scatterplot(inputs[0], inputs[1], inputs[2], hue=label, palette='inferno_r')
    # plt.scatter(inputs[0], inputs[1], inputs[2], hue=label, palette='inferno_r')
    # plt.title('daaaa')
    # plt.show()

    dst = distance.euclidean(a, b)

    fig = plt.figure()
    ax = fig.add_subplot(projection='3d')

    n = 100

    # For each set of style and range settings, plot n random points in the box
    # defined by x in [23, 32], y in [0, 100], z in [zlow, zhigh].

    x, y, z = [], [], []
    c = []
    cm = {'dreapta': 'red', 'clini': 'blue', 'clos': 'yellow'}
    for point in inputs:
        x.append(point[0])
        y.append(point[1])
        z.append(point[2])
        # c.append(outputs)
    print(outputs)
    ax.scatter(x, y, z, marker='o', color=list(map(lambda x: cm[x], outputs)))

    # print(inputs[:][0])
    # print(inputs[:][2])
    # print(inputs)

    ax.set_xlabel('X Label')
    ax.set_ylabel('Y Label')
    ax.set_zlabel('Z Label')

    plt.show()

    if test_x[0] == 0:
        test_out = 'clini'
    elif test_x[0] == 1:
        test_out = 'dreapta'
    else:
        test_out = 'clos'

    return test_out


def get_best_params(train_x, train_y):
    # Best parameters set found on development set:
    # {'activation': 'identity', 'hidden_layer_sizes': (3,), 'solver': 'lbfgs'}
    param_grid = [
        {
            'activation': ['identity', 'logistic', 'tanh', 'relu'],
            'solver': ['lbfgs', 'sgd', 'adam'],
            'hidden_layer_sizes': [
                (1,), (2,), (3,), (4,), (5,), (6,)
            ]
        }
    ]

    clf = GridSearchCV(MLPClassifier(), param_grid, cv=3,
                       scoring='accuracy')
    clf.fit(train_x, train_y)

    print("Best parameters set found on development set:")
    print(clf.best_params_)
