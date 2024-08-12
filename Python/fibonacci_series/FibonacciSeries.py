a=0
b=1
count=0

print(a , b, end=" " )

for i in range (2, 10):
    c = a+b
    print(c, end =" ")
    a=b
    b=c

#output
#0 1 1 2 3 5 8 13 21 34 