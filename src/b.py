import matplotlib.pyplot as plt

X = [0, 10, 15, 20, 25, 30, 31, 32, 33, 35]  # Give all the numbers
Y = [0, 0.5, .95, 23.73, 394.78, 15985.57,
     29522.25, 44630.68, 110705.20, 1184805.80]

# Everytime when you use figure, it creates a new graph

plt.figure(figsize=(4, 5))  # width x height in inches
# marker highlights the point, color changes the color
plt.plot(X, Y, marker='o')

# plt.figure(figsize=(4, 5))  # width x height in inches
# plt.scatter(X, Y, color='red')

# plt.figure(figsize=(4, 5))  # width x height in inches
# plt.bar(X, Y)

plt.xlabel("BitSize")  # sets X axis label, Y axis label and Title
plt.ylabel("Time Taken(in MilliSeconds)")
plt.title("Strength Analysis of RSA Algorithm")


plt.show()  # must be done atlast
