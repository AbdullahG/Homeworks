#include <stdio.h>
#include <stdlib.h>
#include <dirent.h>
#include <unistd.h>
#include <string.h>
#include<sys/timeb.h>

int main(int argc, char *argv[])
{
    if(argc>=3)
    {
        char *text=malloc(sizeof(char*)*strlen(argv[2]));
        strcpy(text,argv[2]);
        FILE *fPtr;
        DIR *dir;
        if ((dir = opendir (argv[1])) != NULL)  // if directory is valid
        {
            struct dirent* ent;
            char temp[512];
            int find_result=0;
            char output[100];
            char *cPtr;
            char *extention = malloc(5);
            extention = ".txt";
            while((ent=readdir(dir))!=NULL)
            {
                char *sub_file;
                sub_file = malloc(sizeof(char*)*(strlen(argv[1])+strlen(ent->d_name)+4));
                strcpy(sub_file,argv[1]);
                if(argv[1][strlen(argv[1])-1]!='/') // if path doesnt ends with '/'
                    strcat(sub_file,"/");
                strcat(sub_file,ent->d_name);

                if( strcmp(".", ent->d_name)!=0 && strcmp("..", ent->d_name)!=0 && (ent->d_type & DT_DIR))
                {
                    int x;
                    x = fork();
                    if(x==0)
                    {
                        char *arguments=NULL;
                        char *program_name= malloc(sizeof(char*)*strlen(argv[0]));
                        strcpy(program_name,argv[0]);
                        arguments = malloc(sizeof(char*)*(strlen(program_name)+5+strlen(ent->d_name)+strlen(text)));
                        strcat(arguments, program_name);
                        strcat(arguments, " ");
                        if(argv[1][strlen(argv[1])-1]!='/') // if path doesnt ends with '/'
                            strcat(argv[1],"/");
                        strcat(argv[1],ent->d_name);
                        strcat(arguments, argv[1]);
                        strcat(arguments, " ");
                        strcat(arguments, text);

                        system(arguments); // argument is like "a.out ./ something" that starts program with new parameters.
                        exit(x);
                    }else{
                    wait(&x);
                    }
                }
                else if(ent->d_name==NULL)
                    continue;

                else if((cPtr = strrchr(ent->d_name,'.'))==NULL)
                    continue;

                else if(strcmp(extention,cPtr)!=0)   // if it is not a txt file then dont read the file.
                    continue;
                else if((fPtr=fopen(sub_file,"r"))!=NULL)
                {
                    find_result=0;
                    while(fgets(temp,512,fPtr)!=NULL)
                    {
                        if(strstr(temp,text)!=NULL)
                        {
                            find_result++;
                        }
                    }
                    if(find_result>0)
                    {
                        strcpy(output,argv[1]);
                        if(argv[1][strlen(argv[1])-1]!='/') // if path doesnt ends with '/'
                            strcat(output,"/");
                        strcat(output,ent->d_name);
                        printf("%s is found %d times in file %s\n",text, find_result,output);
                    }
                }
            }

            closedir (dir);
        }

    }
    else
    {
        printf("Missing parameters.. Send directory and text parameter to search %d.\n",argc);
    }
    return 0;
}
