class Vehicle:
    a=10
    def speed(self):
        print("parent class method")
        return "30 km/h"
    
class Car(Vehicle):
    
    def CarMethod(self):
        print(f"Parent class variable :{self.a}")

c=Car()
c.CarMethod()
c.speed()



    