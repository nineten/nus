#include<stdio.h>

int eax, ebx, ecx, edx, esi, edi ;
unsigned char M[10000];

void exec()
{
	edi = esi;
loop:
	eax += *(unsigned int*)&M[edi];
	edi = *(unsigned int*)&M[edi+4];
	if (edi == 0) goto end_loop;
	goto loop;
end_loop:
	return_p: {}
}

int main()
{
	//random storing of link list in M.
	// add : 100	6567	323		5	13
	// data: 50		32000	1345	177	4689
	esi = 100;
	*(unsigned int*)&M[100] = 50;
	*(unsigned int*)&M[100+4] = 6567;
	*(unsigned int*)&M[6567] = 32000;
	*(unsigned int*)&M[6567+4] = 323;
	*(unsigned int*)&M[323] = 1345;
	*(unsigned int*)&M[323+4] = 5;
	*(unsigned int*)&M[5] = 177;
	*(unsigned int*)&M[5+4] = 13;
	*(unsigned int*)&M[13] = 4689;
	*(unsigned int*)&M[13+4] = 0;

	exec();
	
	printf("%u\n", eax);

	getchar();
}