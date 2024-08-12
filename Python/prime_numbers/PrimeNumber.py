#define the range
lower = 1
upper = 50

def is_prime(n):
    if n==0 or n==1:
        return False
    else:
        for i in range (2,n):
            if n % i == 0:
                return False
    return True

def primeNumbersInRamge(lower, upper):
    for i in range(lower, upper + 1):
        if is_prime(i):
            print(i)

#print the numbers in defined range
primeNumbersInRamge(lower, upper)


#output
# 2
# 3
# 5
# 7
# 11
# 13
# 17
# 19
# 23
# 29
# 31
# 37
# 41
# 43
# 47