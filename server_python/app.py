from flask import Flask, jsonify
import tensorflow as tf
from keras_preprocessing import image
import numpy as np
from flask import request
from flask_cors import CORS

# app = Flask(__name__)
from clustering import plot_data, mlp_classifier, k_means
from preprocessing import criteriu_1, criteriu_4, write_in_csv, criteriu_5

app = Flask(__name__)
cors = CORS(app, resources={r"*": {"origins": "*"}})


@app.route('/get-prediction', methods=['POST'])
def get_graph():
    id = request.json['id']
    vertices = request.json['vertices']
    edges = request.json['edges']
    seams = request.json['seams']

    if len(vertices) > 0:
        difference_height = criteriu_1(vertices, edges, seams)
        difference_x = criteriu_4(vertices, edges, seams)
        no_vertices = criteriu_5(vertices, edges, seams)

        meta_info = [difference_height, difference_x, no_vertices]
        print(meta_info)

        plot_data()
        prediction = k_means(difference_height, difference_x, no_vertices)
        print(prediction)

        if prediction == 'dreapta':
            return jsonify({'type': prediction, 'model': difference_x})

        if prediction == 'clini':
            return jsonify({'type': prediction, 'model': (no_vertices + 2) / 2})

        if prediction == 'clos':
            return jsonify({'type': prediction, 'model': 0})


    return jsonify({'type': 'error'})


if __name__ == '__main__':
    app.run()
