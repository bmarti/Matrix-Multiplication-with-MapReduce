import matplotlib.pyplot as plt
import numpy as np

file = np.loadtxt("MesuresOneStep.txt")
fig = plt.figure(figsize=[15,15])
ax = fig.add_subplot(111)
plt.plot(file[:,0], file[:,1], color = "lightgreen", linewidth = 3)
ax.set(ylabel = "temps (en secondes)", xlabel = "taille de la matrice")
plt.title("Temps d'exécution de l'algorithme en fonction de la taille de la matrice", fontsize=18)
plt.savefig("courbeOS.pdf")
plt.show()
