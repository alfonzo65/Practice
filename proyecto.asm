TITLE	Estacion de Servicio  E/S 		(proyecto.asm)

;Descripcion: Simulacion de una Gasolinera
;Realizado por: jesus morales
; jbecerra@fec.luz.edu.ve

INCLUDE	Irvine32.inc

.data
;---------------------- Variables de Costos -----------------
costo 			DWORD	2 ; x cada litro
total_a_pagar	DWORD	0
ganancia		DWORD   0

;-----------------------VARIABLES dias----------------------
cont_dias	DWORD   0
dias        DWORD   0
avisodia    BYTE    "Dia:",0

;-------------------Variables TOTALES-----------------------
final1			BYTE	"total Vehiculos:",0dh,0ah,0
final2			BYTE    "total litros:",0dh,0ah,0
final3			BYTE	"total surtidos en Estacion 1:",0dh,0ah,0
final4			BYTE    "total surtidos en Estacion 2:",0dh,0ah,0
final5			BYTE	"total Dias:",0dh,0ah,0
final6			BYTE	"total ganancias $:",0dh,0ah,0
total_vehiculos DWORD   0
total_litros	DWORD	0
total_estacion1 DWORD   0
total_estacion2 DWORD   0
total_dias      DWORD   0
total_ganancia	DWORD   0


;---------------------Variables Del Reloj-------------------------
horas		DWORD	7
minutos 	DWORD	0
segundos	DWORD	0

;---------------------Variables de la E/S-------------------------
autoFinal 			DWORD 0
auto	  			DWORD 30000 DUP(0)
puesto 				DWORD 0
estacion1			DWORD 0
estacion2			DWORD 0
litros_totales		DWORD 0
despacho_estacion1	DWORD 0
despacho_estacion2	DWORD 0
cantidad			DWORD 0

;------------------------Saludo Principal----------------------------
aviso1   BYTE	"                                                    ", 0dh, 0ah
		 BYTE	"            * ************************************** *", 0dh, 0ah
		 BYTE	"           * **Bienvenido a la Estacion de Servicio** *", 0dh, 0ah
		 BYTE	"            * ************************************** *", 0dh, 0ah
		 BYTE	"                                                    ", 0dh, 0ah
		 BYTE	"         Cuantos Dias laborables de Servicio desea Simular: ",0
aviso2   BYTE    "Ingresando Vehiculo:",0dh,0ah,0
aviso3   BYTE	 "Cantidad surtida de combustible:",0dh,0ah,0
aviso4   BYTE	 "Despachado por estacion 1:",0dh,0ah,0
aviso5   BYTE	 "Despachado por estacion 2:",0dh,0ah,0
aviso6   BYTE	 "total litros consumidos:",0dh,0ah,0
aviso7   BYTE    "total a pagar = litros x 2$ :",0dh,0ah,0
horario_de_trabajo	BYTE	"  Horario de Trabajo de 7 AM hasta 20 PM",0dh,0ah,0
letrero				BYTE	"*****   Estacion de Servicio E/S Luz   *****",0dh,0ah,0


;---------------------Variables del Archivo-------------------
tam_bufer = 8000
Nombre_archivo		BYTE	"Resultadosdelservicio.txt",0
manejador 			DWORD 	?
bufer				BYTE	tam_bufer DUP (?)
temp 				DWORD	?
msg_error			BYTE    "Error en el archivo",0

;-------------------datos para el ARCHIVO -------------------
total_v		BYTE	"total vehiculos: ",0dh,0ah
total_l		BYTE	"total litros: ",0dh,0ah
total_E1    BYTE	"total autos despachados en la E1: ",0dh,0ah
total_E2	BYTE	"total autos despachados en la E2: ",0dh,0ah
total_d		BYTE	"total dias laborados: ",0dh,0ah
total_g		BYTE	"total Ganancias $: ",0dh,0ah

;------------------Cuadro de MENsaje---------------
leyenda		db		"Mensaje",0
msjhola		BYTE	"Archivo creado con Exito",0dh,0ah

.code

main PROC

	; Color del Texto en pantalla
	call color
	
	;Muestra el Anucio principal
	mov edx,offset aviso1
	call WriteString
	
	;pide al usuario los dias
	call ReadDec					
	mov dias,eax
	
	mov ecx,dias
	cmp ecx,0
	je escape
	
	call Clrscr
	
	
otro_dia:

	;inicio del primer dia
	inc cont_dias			;inicio del primer dia
	
	;reinicia todos los valores 
	mov puesto,0
	mov estacion1,0
	mov estacion2,0
	mov litros_totales,0
	
	mov horas, 7			;Abriendo la estacion de servicio
	mov minutos,0
	mov segundos,0
	
	
		laborar:
		
		call RELOJ
		
		mov eax,horas
		cmp eax,20			; el dia va a iniciarse a las 7 AM y termina a las 20 PM (Horario de Trabajo)
		je continuar_main   ; salta a continuar main si las horas en el registro eax es igual a las 20PM
		jmp laborar			 ;vuelve a repetir el ciclo laborar si la condicion no se ha cumplido
		
		continuar_main:
		
		call totales		; Guarda todo lo relacionado al primer Dia y lo suma en otro variable para ser guardados y sumar nuevamente los datos del siguiente dia
		
		mov eax,cont_dias
		cmp eax,dias
		jne otro_dia 		;salta si el contador de dias es igual a los dias ingresados por el usuario
		jmp archivo_ok
	
archivo_ok:

	call imprimir_totales
	
	;--- AQUI LLAMO AL ARCHIVO ----
	call archivo
	call mensaje
	
	call ReadChar
escape:
	exit

main ENDP

;-------------------RELOJ---------------------------
RELOJ PROC
COMMENT !
	;segundo:
		inc	segundos
		mov eax,segundos
		cmp eax,60
		jb imp
		mov segundos, 0
		;jmp	minuto
		!
		;minuto:
			inc	minutos
			mov eax,minutos
			cmp	eax,60
			jb	imp
			mov minutos,0
			;jmp hora

			hora:
				inc horas
				mov	eax,horas
				cmp	eax,24
				jb	imp
				mov horas,0	
	imp:
	call imprimir_reloj
	
	call Crlf
	
	call generar_autofinal
	
	call generar_cola

	ret
RELOJ ENDP

;-----------------IMPRIMIR Reloj------------------

imprimir_reloj PROC
	
	call tabulador
	
	mov 	edx, offset avisodia
	call 	WriteString
	
	mov 	eax,cont_dias
	call	WriteDec
	
	mov 	al," "
	call	WriteChar
	
	mov		eax,horas
	Call	WriteDec
	mov		al,":"
	call	WriteChar
	mov		eax,minutos
	call	WriteDec
	mov		al,":"
	call	WriteChar
	mov		eax,segundos
	call	WriteDec
	
	
	ret
imprimir_reloj ENDP

;-------------------PROCEDIMIENTO REPOSO--------------------------
reposo PROC

	mov	eax,4
	call delay
	
	ret
reposo ENDP

;----------------------IMPRIMIR MOSTRAR PANTALLA ----------------------
imprimir PROC
	
	;imprime el letrero de bienvenida
	mov edx,offset letrero
	call WriteString
	call Crlf

	;imprime el horario de trabajo
	mov edx,offset horario_de_trabajo
	call WriteString						
	call Crlf

    ;imprime aviso 2
    mov edx,offset aviso2
	call WriteString

	;imprime cada auto que va ingresando
	call WriteDec
	call Crlf

	;imprime los litros surtidos
	mov edx, offset aviso3
	call WriteString
	;imprime la cantidad de combustible de cada auto
	mov eax,cantidad
	call WriteDec
	call Crlf
	
	;imprime mensaje de monto a pagar
	mov	edx,offset aviso7
	call WriteString
	;imprime la cantidad a pagar
	mov	eax,total_a_pagar
	call WriteDec
	call Crlf
	
	;imprime autos depachados por la estacion 1
	mov edx,offset aviso4
	call WriteString
	;imprime los autos despachados por la estacion1
	mov eax,estacion1
	call WriteDec
	call Crlf
	
	;imprime autos depachados por la estacion 2
	mov edx,offset aviso5
	call WriteString
	;imprime los autos despachados por la estacion2
	mov eax,estacion2
	call WriteDec
	call Crlf
	
	;imprime aviso6
	mov edx,offset aviso6
	call WriteString

	;imprime litros consumidos
	mov eax,litros_totales
	call WriteDec
	
	call reposo
	
	ret
	
imprimir ENDP

;------------------------Procedimiento SURTIENDO--------------------
surtiendo PROC
	;seleccion azar de estacion
	mov	eax,2
	call RandomRange
	mov ebx,eax
	cmp ebx,0
	jnz estacion_2
	
	;estacion de servicio 1
	
estacion_1:
	
		mov eax,150
		call RandomRange
		mov cantidad,eax
		call costo_surtido
		;mov cantidad,ebx
		add litros_totales,eax
		inc estacion1
		
	jmp avanza

	;estacion de servicio 2
	
estacion_2:

		mov eax,150
		call RandomRange
		mov cantidad,eax
		call costo_surtido
		;mov cantidad,ebx
		add litros_totales,eax
		inc estacion2
		
	jmp avanza
	
avanza:
		ret

surtiendo ENDP

;-------------------------PROCEDIMIENTO GENERAR EL AUTOFINAL---------------------------

generar_autofinal PROC

	Call Randomize
	mov esi,0
	mov eax,30000    ; Cantidad de Vehiculos aleatoria
	call RandomRange
	mov autofinal,eax
	mov eax,autofinal
	ret
	
generar_autofinal ENDP

;------------------------PROCEDIMIENTO GENERAR COLA de vehiculos -------------------------

generar_cola PROC
	mov esi, OFFSET auto ; declaracion de la primera posicion o direccion de memoria del arreglo auto
	
	;llena la cola de vehiculos desde el 1ro hasta el ultimo(autofinal)
	;cola:
		mov ebx , puesto
		mov [esi], ebx
		mov eax,[esi]
		;call imprimir
		call surtiendo
		add esi, 4
		inc puesto
		mov eax, puesto
		cmp eax, autofinal
		je siguiente ; salta a siguiente si el puesto es igual a autofinal
		;jmp cola
		
	siguiente:
	
	call imprimir
	
	call Clrscr
	
	ret
generar_cola ENDP

;-----------------------PROCEDIMIENTO TABULADOR-------------------------
tabulador PROC
	
	mov dh, 1
	mov dl, 60
	call gotoxy
	ret
	
tabulador ENDP

;----------------------PROCEDIMIENTO REGISTROS , TOTAL de CADA DIA-------------
totales PROC

	mov eax,puesto				;autofinal
	add total_vehiculos,eax
	
	mov eax,litros_totales
	add total_litros,eax
	
;-----USA EL TOTAL DE LITROS "para calcular el total de ganancia"------------
	mov ebx,total_litros
	call ganancia_total
;------------------------------

	mov eax,estacion1
	add total_estacion1,eax
	
	mov eax,estacion2
	add total_estacion2,eax

	mov eax,dias
	mov total_dias,eax
	
	mov eax,ganancia
	mov total_ganancia,eax
	
	call Crlf
	call Crlf
	
	ret
totales ENDP

;---------------------PROCEDIMIENTO COLOR----------------------------------
color PROC
	mov eax,yellow 
	call SetTextColor
	ret
color ENDP


;--------------------- COMIENZO DE LA PESADILLA --------------------------
;--------------------PROCEDIMIENTO ARCHIVO-------------------------------
archivo	PROC
	mov	edx,OFFSET Nombre_archivo
	call	CreateOutputFile
	cmp	eax,INVALID_HANDLE_VALUE
	je	error_archivo
	mov manejador,eax
	
;********imprime el total de vehiculos en el archivo*********
	mov	eax,manejador
		mov	edx,OFFSET total_v
		mov ecx,SIZEOF total_v
		call WritetoFile
		
		mov edx,total_vehiculos
		mov temp,edx
		call convertir
		mov edx, offset bufer
		call StrLength
		mov ecx,eax
		mov eax,manejador
		call Writetofile
		
;*******imprime el total de litros en el archivo*********		
	mov	eax,manejador
		mov	edx,OFFSET total_l
		mov ecx,SIZEOF total_l
		call WritetoFile
		
		mov	edx,total_litros
		mov temp,edx
		call convertir
		mov edx,offset bufer
		call Strlength
		mov ecx,eax
		mov eax,manejador
		call Writetofile
		
;*******imprime cantidad de autos surtidos en la estacion1******
	mov eax,manejador
		mov edx,offset total_E1
		mov ecx,sizeof total_E1
		call Writetofile
		
		mov edx,total_estacion1
		mov temp,edx
		call convertir
		mov edx,offset bufer
		call strlength
		mov ecx,eax
		mov eax,manejador
		call writetofile		
	
;*******imprime cantidad de autos surtidos en la estacion2******
	mov eax,manejador
		mov edx,offset total_E2
		mov ecx,sizeof total_E2
		call Writetofile
		
		mov edx,total_estacion2
		mov temp,edx
		call convertir
		mov edx,offset bufer
		call strlength
		mov ecx,eax
		mov eax,manejador
		call writetofile
		
;*******imprime los dias laborados en el archivo******
	mov eax,manejador
		mov edx,offset total_d
		mov ecx,sizeof total_d
		call Writetofile
		
		mov edx,total_dias
		mov temp,edx
		call convertir
		mov edx,offset bufer
		call strlength
		mov ecx,eax
		mov eax,manejador
		call writetofile		
		
;******imprime el total de las ganancias en el archivo******

	mov eax,manejador
		mov edx,offset total_g
		mov ecx,sizeof total_g
		call Writetofile
		
		mov edx,total_ganancia
		mov temp,edx
		call convertir
		mov edx,offset bufer
		call strlength
		mov ecx,eax
		mov eax,manejador
		call writetofile
		
		call closeFile
		jmp seguir
	
	error_archivo:
	
	mov edx,offset msg_error
	call WriteString
	jmp seguir
	
	seguir:

	ret
archivo ENDP

;----------------PROCEDIMIENTO CONVERTIR------------------(muchas DUDAS X:X)
convertir PROC USES EAX EBX ECX EDX ESI
				mov eax, 0
				mov ebx, 0
				mov edx, 0
				mov ecx, 0
				Dividir:
					inc ebx 
					mov eax, temp
					mov edx, 0 	
					mov ecx, 10
					div ecx 
					add edx, '0'
					push edx
					mov temp, eax
					cmp eax, 0
					jne Dividir
				mov ecx, ebx
				mov esi, OFFSET bufer	
				Guardar:
					pop [esi]
					inc esi
					LOOP Guardar
				mov al, 0Dh
				mov [esi], al
				inc esi
				mov al, 0Ah
				mov [esi], al
				inc esi
				mov al, 0
				mov [esi], al
				ret
convertir ENDP

;--------------------FIN DE LA PESADILLA----------------------------

;--------------------PROCEDIMIENTO IMPRIMIR TOTALES--------------------------
imprimir_totales PROC
	
	mov edx,offset final1
	call WriteString
	mov eax,total_vehiculos
	call WriteDec
	call Crlf
	
	mov edx,offset final2
	call WriteString
	mov eax,total_litros
	call WriteDec
	call Crlf
	
	mov edx,offset final3
	call WriteString
	mov eax,total_estacion1
	call WriteDec
	call Crlf
	
	mov edx,offset final4
	call WriteString
	mov eax,total_estacion2
	call WriteDec
	call Crlf
	
	mov edx,offset final5
	call WriteString
	mov eax,total_dias
	call WriteDec
	call Crlf
	
	mov edx,offset final6
	call WriteString
	mov eax,total_ganancia
	call WriteDec
	call Crlf
	
	ret
imprimir_totales ENDP

;----------------------PROCEDIMIENTO MENSAJE--------------------------
mensaje PROC

	mov ebx,offset leyenda
	mov edx,offset msjhola
	
	call MsgBox
	
	ret
mensaje ENDP

;---------------------PROCEDIMIENTO COSTO-------------------------------

costo_surtido PROC

	mov eax,cantidad
	mul costo

	mov total_a_pagar,eax
	
	ret
costo_surtido ENDP

;-------------PROCEDIMIENTO TOTAL GANANCIA-------------------

ganancia_total PROC
	mov eax,ebx
	mul costo
	
	mov ganancia,eax
	
	ret
ganancia_total ENDP

END main