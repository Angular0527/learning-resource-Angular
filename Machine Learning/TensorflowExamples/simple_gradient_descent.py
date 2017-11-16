"""
Author: Yash Mewada
Github: github.com/yashbmewada
Program for demonstrating simple line fitting using Tensorflow and Gradient Descent Algorithm

This program trains the model to fit two values, slope(m) and x-intercept(b) in the equation 
of line y=mx+b. Here we would provide very small dataset of randomly generated pointset xs and ys 
and train the tensorflow model to adjust the values of m and b in order to fit a straight line.

This straight line can further be used to predict any unknown value Y for a given unknown X based on the
learned value of m and b.
"""
import os 
os.environ['TF_CPP_MIN_LOG_LEVEL']='2' # called in order to minimize the warnings about SSE4.1 instructions.
import tensorflow as tf

"""
Random points of X and Y form the training data. aka Dataset (only training. no validation or test)
"""
xs = [0.00,2.00,4.00,6.00,8.00,10.00,12.00,14.00] #features
ys = [-0.82,-0.90,-0.12,0.26,0.31,0.64,1.02,1.00] #labels (actual outputs)


"""
Initial values for m and b. These values would be adjusted to fit the above dataset point 
"""
m_initial = -0.50
b_initial = 1.00

"""
tf.Variable : allows us to create variables whose values can be adjusted in order to learn at each pass on the dataset.
"""
m = tf.Variable(m_initial)
b = tf.Variable(b_initial)

"""
In order to adjust and fit the line, we try to minimize the "error" between two given values of (x,y) so that the 
line can be fit properly as we minimize the value of distances between our m and b i.e. predicted_y and actual y
(from "ys").
"""
error = 0.0

"""
We write an operation for calculation of error and also iteration over the value of X and Y from the Dataset [xs,ys].
Running this over around 1000 times we would be able to minimize the error to a respecable fit for the line.
"""
for x,y in zip(xs,ys):
    predicted_y = m*x + b
    error += (y-predicted_y)**2  # this is the square of difference of error added to the total error 'cost' which we minimize.

"""
Now, in order to train over this operation set we defined above, we use tensorflow Gradient Descent Optimizer which allows
us to train over this data set and we pass the "error" to the minimize() function of this optimizer as a parameter.abs

here while initialization of the Gradient Descent optimizer, we define a learning_rate = 0.001.

This learning rate defines the magnitude OR "how big" of a jump we want to make while minimizing the "cost" / "error".abs

Remember Too Small a learning rate would make your training very slow and Too big learning rate would make the training never find
an optimum solution. Best Learning Rate can be found by trying different values. Here we take 0.001 randomly as it usually works in 
most cases.
"""
optimizer_op = tf.train.GradientDescentOptimizer(learning_rate=0.001).minimize(error)

"""
Tensorflow uses  a "session" to run the above mentioned training steps.
So before starting the session it is always advisable to initialize variables randomly.
"""
init_op = tf.global_variables_initializer()


"""
All the calculations would now be done in a Session
"""
with tf.Session() as session:
    session.run(init_op)

    _ITERATIONS = 1000 #number of passes on the dataset

    for iteration in range(_ITERATIONS):
        session.run(optimizer_op) #calling our optimization operator to minimize error
    
    slope, intercept = session.run((m,b)) #calling our adjusted values
    print('slope: ', slope , 'Intercept: ', intercept)