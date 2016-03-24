/*
  TP3 - SGM (groupe SR 1.1)
  ANDRIAMILANTO Tompoariniaina
  IHSINE Azzeddine
*/

 // Includes 
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <sys/wait.h>
#include <sys/types.h>
#include <sys/ipc.h>
#include <sys/sem.h>
#include <sys/shm.h>
#include <error.h>   

#define KEY_SEG (key_t) 1234
#define KEY_SEMPH (key_t) 40025
#define SIZE  5 

int id_Seg;
int sem;
int id_pro;
struct sembuf opr = {1, -1, 0};
struct sembuf opr1 = {0, -1, 0};
struct sembuf opr2 = {1, -1, 0};


void producteur(void);
void consommateur(void);
  

int main() {

  //Création d'un segment partagé 
  id_Seg = shmget(IPC_PRIVATE, SIZE * sizeof(int), 0660);
  if(id_Seg < 0){
      perror("shmget: shmget failed"); 
      exit(1)
  }

  sem = semget(IPC_PRIVATE, 3, 0660))
  // Ma sémaphore existe déja  
  if(sem < 0){ 
     semget(IPC_PRIVATE,1,0660); 
  }
  else{ 
      //Initialise notre sémaphore a 1 
      if(semctl(sem,0,SETVAL,1) < 0){
          perror("semclt: semclt failed"); 
          exit(1);
      }
      //Initialisation du sémaphore 2 
      if(semctl(semid, 1, SETVAL, SIZE) < 0){
          perror("semclt: semclt failed"); 
          exit(1);
      }

      //Initialisation du sémaphore 3
      if(semctl(semid, 2, SETVAL, 0) < 0){
          perror("semop: semop failed"); 
          exit(1);
      }
  }

  id_pro = fork();
  if(id_pro == 0){
      producteur();
  }else{
      consommateur(); 
  }

  return 0;

}

void producteur (void)
{
    while(1){
      //Vérification de semop()
      if(semop(semid, &opr2, 1) == -1){
        perror("semop: semop failed"); 
        exit(1);    
      }
      
      //Vérification de semop()
      if(semop(semid, &opr1, 1) == -1){
        perror("semop: semop failed"); 
        exit(1);    
      }       
    }
} 

void consommateur (void) 
{
    while(1){

      //Vérification de semop()
      if(semop(semid, &opr2, 1) == -1){
        perror("semop: semop failed"); 
        exit(1);    
      }
      
      //Vérification de semop()
      if(semop(semid, &opr3, 1) == -1){
        perror("semop: semop failed"); 
        exit(1);    
      }

    } 
}


