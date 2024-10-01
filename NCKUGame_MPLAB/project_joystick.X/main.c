#include "setting_hardaware/setting.h"
#include <stdlib.h>
#include "stdio.h"
#include "string.h"
#define _XTAL_FREQ 1000000
// using namespace std;

char str[20];
/*void Mode1(){   // Todo : Mode1 
    return ;
}
void Mode2(){   // Todo : Mode2 
    return ;
}*/
void main(void) 
{
    SYSTEM_Initialize() ;
    TRISBbits.RB0 = 1;
    RCONbits.IPEN = 0;
    INTCONbits.GIE = 1;
    INTCONbits.INT0IE = 1;
    INTCONbits.INT0IF = 0;
    
    while(1) {
        int x_val = ADC_Read(1);
        int y_val = ADC_Read(0);
        
        if(500 <= x_val && x_val <= 520 && 500 <= y_val && y_val <= 520){   //neutral
             UART_Write('t');
            //UART_Write('B');
        }else if(700 <= y_val){ //down
              UART_Write('a'); 
             // UART_Write('[');     
          //    UART_Write('B'); 
//            UART_Write('D');
//            UART_Write('0');
//            UART_Write('W');
//            UART_Write('N');
        }else if(350 >= y_val){ //up
            UART_Write('d');  
           // UART_Write('[');     
           // UART_Write('A'); 
//            UART_Write('U');
//            UART_Write('P');
        }else if(700 <= x_val){ //right
            UART_Write('s');
          //  UART_Write('[');     
          //  UART_Write('C'); 
//            UART_Write('R');
//            UART_Write('I');
//            UART_Write('G');
//            UART_Write('H');
//            UART_Write('T');
        }else if(350 >= x_val){//left
            UART_Write('w'); 
        //    UART_Write('[');     
         //   UART_Write('D'); 
//            UART_Write('L');
//            UART_Write('E');
//            UART_Write('F');
//            UART_Write('T');
        }else{
            UART_Write('t');
        }
        
        __delay_ms(500);
        
    }
    return;
}

void __interrupt(high_priority) Hi_ISR(void)
{
    
    if(INTCONbits.INT0IF)
    {
      UART_Write('x'); 
      INTCONbits.INT0IF = 0;
    }
    __delay_ms(500);
    
    return;
}
/*
 void __interrupt(high_priority) Hi_ISR(void)
{
    if(PIR1bits.CCP1IF == 1) {
        RC2 ^= 1;
        PIR1bits.CCP1IF = 0;
        TMR3 = 0;
    }
    return ;
}*/

