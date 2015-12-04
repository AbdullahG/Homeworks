#include <stdio.h>
#include <stdlib.h>
#include <dirent.h>
#include <unistd.h>
#include <string.h>
void openDirSearchText(char *path, char* text);
int main(int argc, char *argv[])
{
    if(argc>=3)
    {
        DIR *dir;

        if ((dir = opendir (argv[1])) != NULL)
        {
            openDirSearchText(argv[1],argv[2]);
            closedir(dir);
        }
        else
        {
            printf("Could not Open the Directory");
            return EXIT_FAILURE;
        }
    }
    else
    {
        printf("Missing arguments.. Please send directory and text parameter to search.");
    }


    return 0;
}

void openDirSearchText(char *path, char* text)
{
    struct dirent* ent;
    FILE *fPtr;
    char temp[512];
    int find_result=0;
    char *extention = malloc(sizeof(char*)*strlen(".txt"));
    extention=".txt";
    char *cPtr;
    DIR *dir = opendir(path);
    if(dir!=NULL)
        while((ent=readdir(dir))!=NULL)
        {
            if(strcmp(ent->d_name,".")==0 || strcmp(ent->d_name,"..")==0)
                continue;

            char *sub_file;
            sub_file = malloc((strlen(path)+3+strlen(ent->d_name))*sizeof(char*));
            strcpy(sub_file,path);

            if(sub_file[strlen(sub_file)-1]!='/') // if it's last char is not '/'
            strcat(sub_file,"/");

            strcat(sub_file,ent->d_name);
            if(ent->d_type & DT_DIR)
            {
                openDirSearchText(sub_file,text);
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
                    printf("%s is found %d times in file %s/%s\n",text,find_result,path,ent->d_name);
                }
            }
            free(sub_file);
        }
        closedir(dir);
}
