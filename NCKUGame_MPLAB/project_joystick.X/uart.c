#include <xc.h>
    //setting TX/RX

char mystring[20];
int lenStr = 0;

void UART_Initialize() {
           
    /*       TODObasic   
           Serial Setting      
        1.   Setting Baud rate
        2.   choose sync/async mode 
        3.   enable Serial port (configures RX/DT and TX/CK pins as serial port pins)
        3.5  enable Tx, Rx Interrupt(optional)
        4.   Enable Tx & RX
          */ 
    TRISCbits.RC6 = 1 ;  //TX          
    TRISCbits.RC7 = 1 ;  //RX      
    
    //  Setting baud rate   //1200
    TXSTAbits.SYNC = 0;      //asynchronous mode = 0     
    BAUDCONbits.BRG16 = 0;          
    TXSTAbits.BRGH = 0;
    SPBRG = 51;      //**** //only need to modify this
    
   //   Serial enable
    RCSTAbits.SPEN = 1;         //enable asynchronous serial port    //open once
    RCSTAbits.CREN = 1;             //will be cleared when error occured    //open once
    TXSTAbits.TXEN = 1;           //enable transmission //open once
//    PIR1bits.TXIF = 1;          //**** //flag bit  //1:txreg = blank
//    PIR1bits.RCIF = 0;              //receiver flag bit  //set when reception is complete   
//    IPR1bits.RCIP = 0;    // high priority interrupt
//    PIE1bits.RCIE = 1;      //receiver interrupt enable bit 
//    IPR1bits.TXIP = 0;      //low priority interrupt
//    PIE1bits.TXIE = 0;    //interrupt  //when this pin is on and TXIF is 1, then the interrupt is activated       
    }

void UART_Write(unsigned char data)  // Output on Terminal  //sender/transmitter
{
    while(!TXSTAbits.TRMT); //TRMT reg is set when TSR reg(data that gonna transferred) is empty
    TXREG = data;              //write to TXREG will send data 
}


/*void UART_Write_Text(char* text) { // Output on Terminal, limit:10 chars
    for(int i=0;text[i]!='\0';i++)
        UART_Write(text[i]);
}*/

void ClearBuffer(){
    for(int i = 0; i < 10 ; i++)
        mystring[i] = '\0';
    lenStr = 0;
}

void MyusartRead()  //receiver
{
     //TODObasic: try to use UART_Write to finish this function 
    mystring[lenStr] = RCREG;
    if (mystring[lenStr] == '\r')
        UART_Write('\n');
    UART_Write(mystring[lenStr]);
    
    //UART_Write(RCREG);
    lenStr++;   //???
    lenStr %= 10;   //???
    
    return ;
}

char *GetString(){
    
    return mystring;
   
}

/*void __interrupt(high_priority) Hi_ISR(void)
{
    INTCONbits.GIEH = 0;
    if(PIR1bits.RCIF)
    {
      UART_Write(&data);
      PIR1bits.RCIF = 0;
    }
    INTCONbits.GIEH = 1;
}*/
// void interrupt low_priority Lo_ISR(void)
//void __interrupt(low_priority)  Lo_ISR(void)
//{
//    if(PIR1bits.RCIF){
//        if(RCSTAbits.OERR)
//            {
//                CREN = 0;
//                Nop();
//                CREN = 1;
//            }
//        
//        MyusartRead();
//    }
//    //wprocess other interrupt sources here, if required
//    return;
//}