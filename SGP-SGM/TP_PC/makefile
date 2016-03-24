# TP3 - SGM (groupe SR 1.1)
# ANDRIAMILANTO Tompoariniaina
# IHSINE Azzeddine

# Global vars
CFLAGS = -g -Wall


# Compilations
all: emetteur recepteur

emetteur: emetteur.c
	gcc $(CFLAGS) -o emetteur emetteur.c

recepteur: recepteur.c
	gcc $(CFLAGS) -o recepteur recepteur.c


# Clean the workspace
clean: mrproper

mrproper:
	rm emetteur recepteur