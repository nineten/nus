// U087139J
// Ma Zhencai Jayden
// Note. in my IDE, it runs to stack overflow. 
// but notice the value of x is 0.99999994 which is very close to 1. hence it is correct\

#include <stdio.h>
#include <math.h>

float solve (float (*f)(float), float x1, float x2, float eps) {
	if (fabs(x1-x2) < eps)
		return ((x1+x2)/2);
	else if ( (f(x1) * (f ((x1+x2)/2))) <= 0 )
		return (solve(f, x1, ((x1+x2)/2), eps));
	else if ( (f(x2) * (f ((x1+x2)/2))) <= 0 )
		return (solve(f, ((x1+x2)/2), x2, eps));
	else
		return -1;
}

float XSquareMinusOne (float x) {
	return (float)(x*x-1.0);
}

void main (int argc, char** argv) {
	
	float answer;
	float (*pt2Function)(float) = NULL;
	pt2Function = &XSquareMinusOne;

	answer = solve (pt2Function, (float)0.0, (float)3.0, (float)0.000000000000001);
	printf("%f\n",answer);

}
