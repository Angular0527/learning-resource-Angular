/*
Write a program to create a graph in adjacency list structure (Node
directory structure). Traverse it in BFS(Breadth First Search).

@Author : Chintan H Rathod

*/


#include<iostream.h>
#include<conio.h>
#include<ctype.h>

#define SIZE 20

int size=0;
int q[20],front=-1,rear=-1;
void QueueInsert(int);
int QueueDelete();
int QueueSearch(int);
void QueueDisp();
void DispArray(int [],int);

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

void DispNodeFlag()
{
	cout<<"\nNode directory....\n";
	for(int i=0;i<size;i++)
		cout<<nd[i].flag<<" ";
}

int GetNodeStatus()
{
	for(int i=0;i<size;i++)
	{
		if(nd[i].flag==0)
			return(0);
	}
	return(1);
}

void BFS()
{
	int item;
	int arr[20],k=0;

	cout<<"\nEnter Item To Search BFS : ";
	cin>>item;

	struct List *ltemp;
	int d;

	while(1)
	{

	  arr[k++]=nd[item].data;
	  nd[item].flag=1;
	  ltemp=nd[item].ptr;

	  while(ltemp!=NULL)
	  {
		QueueInsert(ltemp->data);
		ltemp=ltemp->ptr;
	  }

	  DispNodeFlag();
	  d=GetNodeStatus();

	  if(d==1)
	  {
		break;
	  }

	  do
	  {
		item=QueueDelete();
	  }while(nd[item].flag!=0 && item!=-1);

	}

	cout<<"\n\nCreated BFS....\n\n";
	for(int i=0;i<k;i++)
	{
		cout<<arr[i]<<" ";
	}
}

void QueueInsert(int item)
{
	if(rear>SIZE)
	{
		//cout<<"\nQueue Full";
		return;
	}
	if(rear==-1 && front==-1)
		front=0;
	rear=rear+1;
	q[rear]=item;
}

int QueueDelete()
{
	if(front==-1)
	{
		//cout<<"\nQueue Empty";
		return(-1);
	}

	int item=q[front];
	if(rear==front)
	{
		rear=front=-1;
	}
	else
	{
		front=front+1;
	}
	return(item);
}

int QueueSearch(int item)
{
	for(int i=rear;i<front;i++)
	{
		if(item==q[i])
			return(1);
	}
	return(0);
}

void QueueDisp()
{
	for(int i=rear;i<front;i++)
	{
		cout<<q[i]<<" ";
	}
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
	BFS();

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

Enter Item To Search BFS : 0

		   (0 1 2 3 4 5 6 7)
Node directory....: 1 0 0 0 0 0 0 0

Node directory....: 1 1 0 0 0 0 0 0

Node directory....: 1 1 1 0 0 0 0 0

Node directory....: 1 1 1 0 0 0 0 1

Node directory....: 1 1 1 1 0 0 0 1

Node directory....: 1 1 1 1 1 0 0 1

Node directory....: 1 1 1 1 1 1 0 1

Node directory....: 1 1 1 1 1 1 1 1
-----------------------------------
Created BFS....   : 0 1 2 7 3 4 5 6

*/

