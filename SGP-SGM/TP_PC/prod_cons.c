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

#define SIZE  5  

int semid;
int shmid;
sembuf opr1;
sembuf opr2;
sembuf opr3;
//Mémoire partagée 
struct shmid_ds shmid_ds, *buf;
buf = & shmid_ds;    
   
//Avoir un identifiant de sémaphore && Vérification de semget()
if(semid = semget(IPC_PRIVATE, 3, 0660) == -1){
  perror("shmget: shmget failed"); 
  exit(1); 
} 

//Initialisation du sémaphore 1  
if(semctl(semid, 0, SETVAL, 1) == -1){
  perror("semclt: semclt failed"); 
  exit(1);
} 

//Initialisation du sémaphore 2 
if(semctl(semid, 1, SETVAL, SIZE)){
  perror("semclt: semclt failed"); 
  exit(1);
}

//Initialisation du sémaphore 3
if(semctl(semid, 2, SETVAL, 0) == -1){
  perror("semop: semop failed"); 
  exit(1);
} 

//Création d'un segment de mémoire partagée
if ((shmid = shmget(IPC_PRIVATE, SIZE, 0660)) == -1) {
  perror("shmget: shmget failed");
  exit(1);
}

void producteur (void)
{
    //Nmuéro de sémaphore
    opr1.sem_num = 0;
    //Opératoin sur la sémaphore P()
    opr1.sem_op = -1;
    opr1.sem_flg = 0; 

    //Nmuéro de sémaphore
    opr2.sem_num = 1;
    //Opératoin sur la sémaphore P()
    opr2.sem_op = -1;
    opr2.sem_flg = 0;

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
    //Nmuéro de sémaphore
    opr.sem_num = 1;
    //Opératoin sur la sémaphore P()
    opr.sem_op = -1;
    opr.sem_flg = 0;
 
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

int main() {

  
  return 0;

}


