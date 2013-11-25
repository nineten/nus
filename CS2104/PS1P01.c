#include<stdio.h>

int eax, ebx, ecx, edx, esi, edi ;
unsigned char M[10000];

void exec()
{
	//edi as a counter.
	edi = 0;
loop:
	edx = M[esi];
	edx &= 0xF; //converts ascii to int
	eax += edx;
	esi += 1;
	edx = M[esi];
	if (edx == 0) goto end_loop;
	eax *= 10; // multiply eax by 10
	goto loop;
end_loop:

	return_p: {}
}

int main()
{
	esi = 0;
	// sets a string 519 in memory address at 0;
	M[esi] = '5';
	M[esi+1] = '1';
	M[esi+2] = '9';
	M[esi+3] = '\0';
	
	exec();
	printf("%u\n", eax);

	getchar();
}