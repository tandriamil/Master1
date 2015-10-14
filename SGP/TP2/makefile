# TP2 - SGF (groupe SR 1.1)
# ANDRIAMILANTO Tompoariniaina
# IHSINE Azzeddine

# Global vars
CFLAGS = -g -Wall


# Compilations
all: div0 tictac tictacforever monitor

div0: div0.c
	gcc $(CFLAGS) -o div0 div0.c

tictac: tictac.c
	gcc $(CFLAGS) -o tictac tictac.c

tictacforever: tictacforever.c
	gcc $(CFLAGS) -o tictacforever tictacforever.c

monitor: monitor.c
	gcc $(CFLAGS) -o monitor monitor.c


# Clean the workspace
clean:
	rm div0
	rm tictac
	rm tictacforever
	rm monitor