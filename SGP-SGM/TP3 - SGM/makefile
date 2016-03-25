# TP3 - SGM (groupe SR 1.1)
# ANDRIAMILANTO Tompoariniaina
# IHSINE Azzeddine

# Global vars
CFLAGS = -g -Wall


# Compilations
all: emetteur recepteur prod_cons

emetteur: emetteur.c
	gcc $(CFLAGS) -o emetteur emetteur.c

recepteur: recepteur.c
	gcc $(CFLAGS) -o recepteur recepteur.c

prod_cons: prod_cons.c
	gcc $(CFLAGS) -o prod_cons prod_cons.c


# Clean the workspace
clean: mrproper

mrproper:
	rm emetteur recepteur prod_cons