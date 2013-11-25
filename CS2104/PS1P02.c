#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <string.h>

//char names[ NUM_NAMES ][ MAX_NAME_LENGTH ];
char varList[1000][1000];
unsigned int varValue[1000];
int state = 0, sizeOfList = 0;
//State 0: takes in 1st variable.
//State 1: + mode. takes in the operand.
//State 2: - mode. takes in the operand.
//State 3: * mode. takes in the operand.
//State 4: / mode. takes in the operand.

int main()
{
	char c;
	unsigned int value1 = 0, value2 = 0;
	int counter = 0, temp = 0, var2IsInt = -1, var2IsNegative = -1, i;
	char var1[1000], var2[1000];

	while( (c=getchar()) != '\n' )
	{
		switch (c)
		{
		case '+': 
			state = 1;
			var1[counter] = '\0';
			temp = checkName(var1);
			if (temp != -1) // checks if the variable is found or not.
				value1 = varValue[temp];
			else // creates a new variable to the list of the variable is not found
			{
				newVar(var1,0);
				value1 = 0;
			}
			getchar(); //gets away the =
			counter = 0;
			break;
		case '-':
			if (state == 0)
			{
				state = 2;
				var1[counter] = '\0';
				temp = checkName(var1);
				if (temp != -1) // checks if the variable is found or not.
					value1 = varValue[temp];
				else // creates a new variable to the list of the variable is not found
				{
					newVar(var1,0);
					value1 = 0;
				}
				getchar(); //gets away the =
				counter = 0;
			}
			else // detects a - sign after the operator (handles negative by flagging it)
			{
				var2IsNegative = 1;
			}
			break;
		case '*':
			state = 3;
			var1[counter] = '\0';
			temp = checkName(var1);
			if (temp != -1) // checks if the variable is found or not.
				value1 = varValue[temp];
			else // creates a new variable to the list of the variable is not found
			{
				newVar(var1,0);
				value1 = 0;
			}
			getchar(); //gets away the =
			counter = 0;
			break;
		case '/':
			state = 4;
			var1[counter] = '\0';
			temp = checkName(var1);
			if (temp != -1) // checks if the variable is found or not.
				value1 = varValue[temp];
			else // creates a new variable to the list of the variable is not found
			{
				newVar(var1,0);
				value1 = 0;
			}
			getchar(); //gets away the =
			counter = 0;
			break;
		case ';':
			var2[counter] = '\0';
			if (var2IsInt != 0) // Checks if the operand 2 is flagged as an int
			{
				value2 = atoi(var2);
				if (var2IsNegative == 1) // Checks if negative is flagged.
					value2 *= -1;
			}
			else
			{
				temp = checkName(var2);
				if (temp != -1) // checks if the variable is found or not.
					value2 = varValue[temp];
				else // creates a new variable to the list of the variable is not found
				{
					newVar(var2,0);
					value2 = 0;
				}
			}
			switch (state)
			{
			case 1: value1 += value2; break;
			case 2: value1 -= value2; break;
			case 3: value1 *= value2; break;
			case 4: value1 /= value2; break;
			}
			storeVarValue(var1, value1); // stores the outcome back to the variable in the list
			//reset variables used for next command
			state = 0;
			counter = 0;
			var2IsInt = -1;
			var2IsNegative = -1;
			break;
		default:
			if (state == 0) // if the first operand is not finished reading.
			{
				var1[counter] = c;
			}
			else
			{
				if (var2IsInt == -1) // if the 2nd operand is not checked for digit
				{
					if (isdigit(c)) // checks if the character is a digit
					{
						var2IsInt = 1;
					}
					else
						var2IsInt = 0;
				}
				var2[counter] = c;
			}
			counter++;
		}
	}// end while

	for(i=0; i<sizeOfList; i++)
	{
		printf("%s = %d ", varList[i],varValue[i]);
	}
	printf("\n");
	
	getchar();
}

// function to store the variable's value
int storeVarValue(char tempVar[], unsigned int tempValue)
{
	int i;
	i = checkName(tempVar);
	if (i!=-1)
	{
		varValue[i] = tempValue;
	}
	else
	{
		newVar(tempVar,tempValue);
	}
	return 1;
}

// function to create a new variable
int newVar(char tempVar[], unsigned int tempValue)
{
	int x;
	sizeOfList++;
	for (x=0; x<strlen(tempVar); x++)
		varList[sizeOfList-1][x] = tempVar[x];
	varValue[sizeOfList-1] = tempValue;
	return 1;
}

// function to check if the variable already exist in the list
int checkName(char tempVar[])
{
	int x,i;
	for(i=0; i<sizeOfList; i++)
	{
		for(x=0; x<strlen(tempVar); x++)
		{
			if (tempVar[x] == varList[i][x])
				if ((x==(strlen(tempVar)-1)) && (strlen(tempVar)==strlen(varList[i])))
					return i;
		}
	}
	return -1;
}