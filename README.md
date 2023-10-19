# generate-patterns-from-photos

This project was my bachelor's thesis, which proposed an application for generating basic garment patterns for three types of skirts (straight skirt, A-line skirt, and pleated skirt). The chosen types of skirts are the most general, making our approach inherently versatile.

The highlights of this project are:
* Creating a graph from the 3D points corresponding to the critical imaginary points (the points that determine the ends and curvature of a line) on the seams used for modeling a clothing item.
* Using clustering for the different types of skirts.
* Creating a dataset from the generated patterns from the application.
* Processing the patterns using proprietary algorithms created based on real-world practices.
![image](https://github.com/AndreeaIloaia/generate-patterns-from-photos/assets/45978765/4df9a75a-ac72-42d7-90a1-27275b2baf5c)
  
To simulate the 3D environment, we used a frontend framework called [Three.js](https://threejs.org/examples/#webgl_geometry_spline_editor), which allows the loading of 3D objects (manipulating the camera perspective and focus, as well as obtaining points and lines in 3D). For our model, we opted for a female model, representing the entire body in an "A" pose, generated from the website [clara.io](https://clara.io/login). Each identified seam corresponds to a 3D representation, represented by a group of spline curves.
![image](https://github.com/AndreeaIloaia/generate-patterns-from-photos/assets/45978765/db729233-9365-456d-81f9-dfdf31d5e6ef)

For classifying the type of skirt, we chose an intelligent algorithm that employs unsupervised learning, as the training data is limited and diverse. We used the k-means algorithm from the sklearn library to classify into three categories. So, the algorithm takes the graph of points and their connections as input and returns the predicted type of skirt. 

All calculations for generating the patterns were done on the frontend and are generated at a specific scale to make them visually pleasing for the user.

Technologies:
* Java
* Spring Boot
* Angular
* RESTful APIs
* PostgreSQL
* JUnit, and MockMvc.
* Python

The result was an image that was vectorized using Inkscape editor, a process that was automatized using UIPath. The image was printed and used by a tailor to create a dress.
![image](https://github.com/AndreeaIloaia/generate-patterns-from-photos/assets/45978765/6204fa80-a24f-41a2-b47d-5aab29db3c67)
 
