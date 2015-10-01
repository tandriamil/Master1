# TP1 - SGF (groupe SR 1.1)
# ANDRIAMILANTO Tompoariniaina
# IHSINE Azzeddine

# Global vars
CFLAGS = -g -Wall


# Compilations
all: partie1 partie2 prodcons

partie1: partie1.c
	gcc $(CFLAGS) -o partie1 partie1.c

partie2: partie2.c
	gcc $(CFLAGS) -o partie2 partie2.c

prodcons: prodcons.c
	gcc $(CFLAGS) -o prodcons prodcons.c


# Clean the workspace
clean:
	rm partie1
	rm partie2
	rm prodcons


# Test programs
test_partie1:
	./partie1

test_prodcons:
	./prodcons