/*
Write a program to create a graph in adjacency list structure (Node
directory structure). Traverse it in DFS(Depth First Search).

@Author : Chintan H Rathod

*/


#include<iostream.h>
#include<conio.h>
#include<ctype.h>

#define SIZE 20

int size=0;
int stack[20],top=-1;

void Push(int);
int Pop();

struct List
{
	int data;
	struct List *ptr;
}*lt;

struct NodeDir
{
	int data;
	int flag;
	struct List *ptr;
}*nd;

void CreateNodeDir(int n)
{
	nd=new struct NodeDir[n];
	
	size=n;
	for(int i=0;i<n;i++)
	{
		nd[i].data=i;
		nd[i].flag=0;
		nd[i].ptr=NULL;
	}
}

void DispNodeDir()
{
	cout<<"\nNode Directory Structure...\n";
	for(int i=0;i<size;i++)
	{
		cout<<"Node "<<i<<" : "<<nd[i].data<<endl;
	}
}

void CreateList()
{
	char ch;
	int arr[30]={1,2,7,0,3,4,0,3,5,1,2,7,1,6,2,6,3,4,5,0,3},k=0;
	//int arr[30]={1,2,3,4,3,5,7,6,6,3,0},k=0;
	//int arr[30]={1,3,0,2,4,1,3,4,0,2,4,1,2,3},k=0;
	int item;
	struct List *temp;

	for(int i=0;i<size;i++)
	{
		clrscr();
		cout<<"\nEnter Information Of Node : "<<i<<endl;
		cout<<"\nEnter 'y' To Insert Node : ";
		cin>>ch;

		while(tolower(ch)=='y')
		{
			temp=nd[i].ptr;

			cout<<"Enter Node Data : ";
			//cin>>item;
			item=arr[k++];
		  if(item>size)
		  {
			cout<<"\nNode Index Must Be In Range....";
		  }
		  else
		  {
			lt=new struct List();
			lt->data=item;

			if(nd[i].ptr==NULL)
			{
				nd[i].ptr=lt;
				lt->ptr=NULL;
			}
			else
			{
			   while(temp->ptr!=NULL)
				temp=temp->ptr;

			   temp->ptr=lt;
			   lt->ptr=NULL;
			}
		  }
			cout<<"\nEnter 'y' To Insert Nodes : ";
			cin>>ch;
		}
	}
}

void DispList()
{
	struct List *temp;
	for(int i=0;i<size;i++)
	{
		temp=nd[i].ptr;
		cout<<"\n<< Node Dir "<<nd[i].data<<" >> ";
		while(temp!=NULL)
		{
			cout<<"-> "<<temp->data;
			temp=temp->ptr;
		}
	}
}

void DFS()
{
	int item;

	cout<<"\nEnter Item To Search DFS : ";
	cin>>item;

	struct List *ltemp;

	Push(nd[item].data);
	nd[item].flag=1;
	cout<<endl<<item;
	while(top != -1)
	{
	  item = Pop();
	  ltemp=nd[item].ptr;
	  while(ltemp != NULL)
	  {
		if(nd[ltemp->data].flag == 0)
		{
		  cout<<"  "<<nd[ltemp->data].data;
		  nd[ltemp->data].flag = 1;
		  if(ltemp->ptr != NULL)
		  {
			Push(ltemp->data);
		  }
		  ltemp = nd[ltemp->data].ptr;
		}
		else
		{
		  ltemp=ltemp->ptr;
		}
	  }
	}
}

void Push(int item)
{
	if(top>SIZE)
	{
		//cout<<"\nQueue Full";
		return;
	}
	top=top+1;
	stack[top]=item;
}

int Pop()
{
	if(top==-1)
	{
		//cout<<"\nQueue Empty";
		return(-1);
	}

	int item=stack[top];
	top=top-1;
	return(item);
}

void main()
{
	clrscr();
	int n;
	cout<<"Enter Number Of Node To Create : ";
	cin>>n;
	
	CreateNodeDir(n);
	CreateList();
	DispList();
	DFS();

	getch();
}

/*

OUTPUT
------

<< Node Dir 0 >> -> 1-> 2-> 7
<< Node Dir 1 >> -> 0-> 3-> 4                                                   
<< Node Dir 2 >> -> 0-> 3-> 5                                                   
<< Node Dir 3 >> -> 1-> 2-> 7                                                   
<< Node Dir 4 >> -> 1-> 6                                                       
<< Node Dir 5 >> -> 2-> 6                                                       
<< Node Dir 6 >> -> 3-> 4-> 5                                                   
<< Node Dir 7 >> -> 0-> 3                                                       

Enter Item To Search DFS : 0
  0  1  2  3  7  5  6  4

*/