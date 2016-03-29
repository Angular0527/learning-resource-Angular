/*
Program for Max Heap Tree

@Author : chintan Rathod
*/

#include<iostream.h>
#include<conio.h>
#define size 20

int a[size],n=0;
void disp();

void max_insert(int item)
{
	int i,p,temp;
	if(n>size)
	{
		cout<<"\nHeap Tree is saturated: Insertion is Void";
		return;
	}
	else
	{
		n=n+1;
		a[n]=item;
		i=n;
		p=i/2;
		disp();
		while((p>0) && (a[p]<a[i]))
		{
			temp=a[i];
			a[i]=a[p];
			a[p]=temp;
			i=p;
			p=p/2;
		}		
	}
}

void max_del()
{
	int item;
	if(n==0)
	{
		cout<<"\nHeap tree is exhausted: Deletion is NOt Possible";
		return;
	}
	item=a[1];
	a[1]=a[n];
	
	n=n-1;
	int flag=0,i=1,lchild,rchild;
	int x,y,temp;
	
	while(flag==0 && i<n)
	{
		lchild=2*i;
		rchild=2*i+1;
		
		if(lchild<=n)
			x=a[lchild];
		else
			x=-a[lchild];
		
		if(rchild<=n)
			y=a[rchild];
		else
			y=-a[rchild];
			
		if(a[i]>x && a[i]>y)
			flag=1;
		else
		{
			if(x>y && a[i]<x)
			{
				temp=a[lchild];
				a[lchild]=a[i];
				a[i]=temp;
				i=lchild;
			}
			else if(y>x && a[i]<y)
			{
				temp=a[rchild];
				a[rchild]=a[i];
				a[i]=temp;
				i=rchild;
			}
		}
	}
}

void disp()
{
	cout<<"\nMax Heap....\n";
	for(int i=1;i<=n;i++)
	{
		cout<<a[i]<<" ";
	}
	cout<<endl;
}

void main()
{
	clrscr();
	int ch=1,item;
	
	while(ch!=0)
	{
		cout<<"\n1.Insert\n2.Delete\n3.Display";
		cout<<"\n0.Exit";
		cout<<"\nEnter Your Choice : ";
		cin>>ch;
		
		switch(ch)
		{
		case 1:
			cout<<"\nEnter Item : ";
			cin>>item;
			max_insert(item);
			disp();
			break;
		case 2:
			max_del();
			break;
		case 3:
			disp();
			break;
		default:
			break;
		}
		
	}
}
