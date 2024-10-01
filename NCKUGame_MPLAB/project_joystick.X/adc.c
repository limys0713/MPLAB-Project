#include <xc.h>

void ADC_Initialize(void) {
    //set input pin
    TRISAbits.RA0 = 1 ;         
    TRISAbits.RA1 = 1 ;  
    ADCON1 = 0x0d;  	// set AN1/AN0 as analag(RAO/RA1) 	
    ADCON2bits.ADFM = 1;          // Right Justified ADC result
    ADCON2bits.ACQT = 0b010; // Acquisition time select (4 TAD)
    ADCON2bits.ADCS = 0b100;  //4MHZ OSC 
    ADCON0bits.ADON = 1; // Enable ADC module
    ADRESH=0;  			// Flush ADC output Register
    ADRESL=0;  
}

int ADC_Read(int channel)
{
    int digital;
    
    ADCON0bits.CHS = channel;   //read the input channel
    //ADCON0bits.CHS =  0x07; // Select Channel
    ADCON0bits.GO = 1;
    ADCON0bits.ADON = 1;
    
    while(ADCON0bits.GO_nDONE==1);

    //return 10-bit ADC result
    digital = (ADRESH*256) + (ADRESL);
    return(digital);
}