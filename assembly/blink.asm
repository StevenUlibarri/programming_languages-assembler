/*set pin 47 output*/
MOVW R4, 0
MOVT R4, 0x3F20
ADD R2, R4, 0x10
LDR R3, R2
OR R3, R3, 0x200000
STR R3, R2

/*turn on LED*/
LOOP: ADD R3, R4, 0x20
MOVW R2, 0x8000
STR R2, R3

/*delay*/
MOVW R5, 0
MOVT R5, 0x10
WAIT1: SUBS R5, R5, 1
BNE WAIT1

/*turn off pin*/
ADD R3, R4, 0x2C
MOVW R2, 0x8000
STR R2, R3

/*delay again*/
MOVW R5, 0
MOVT R5, 0x10
WAIT2: SUBS R5, R5, 1
BNE WAIT2

/*loop to turn on LED*/
BAL LOOP