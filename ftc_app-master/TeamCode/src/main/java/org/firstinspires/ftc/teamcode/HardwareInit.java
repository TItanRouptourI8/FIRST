package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class HardwareInit
{
    private HardwareMap map;


    private static  ArrayList<DcMotor> motors = new ArrayList<>();
    static int AVD = 0, AVG = 1, ARD = 2,ARG = 3, OUV = 4, MOISSONEUSE = 4,ASCG = 5, ASCD = 6;

    private String[] motorsName = new String[] {"AVD","AVG","ARD","ARG","ROULETTE","ASCG","ASCD"};
    private int nbreMoteurs = motorsName.length;


    //CONSTRUCTEUR

    HardwareInit(HardwareMap map)
    {
        this.map = map;

        Initialisation();
    }


    //INITIALISATION DES MOTEURS DANS L'ARRAY 'motors'

    private void Initialisation()
    {
        for (String name : motorsName)
        {
            DcMotor motor = map.dcMotor.get(name);
            motor.setDirection(DcMotorSimple.Direction.FORWARD);
            motors.add(motor);
        }
        // ATTENTION : l'un des moteurs ASC G ou D doit être mis en reverse
        // *** Initialise les encodeurs des deux moteurs
        motors.get(ASCG).setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motors.get(ASCD).setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // *** Indique au moteur gauche qu'il doit utiliser les encodeurs

        motors.get(ASCG).setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // *** Tentative simpliste d'asservissement du moteur droit :

        motors.get(ASCD).setMode(DcMotor.RunMode.RUN_TO_POSITION);



    }

    public  ArrayList<DcMotor> getMotors() {
        return motors;
    }

}
