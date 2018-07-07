package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class HardwareInit
{
    private HardwareMap map;


    private static  ArrayList<DcMotor> motors = new ArrayList<>();
    static int AVD = 0, AVG = 1, ARD = 2,ARG = 3, MOISSONEUSE = 4,ASCG = 5, ASCD = 6;

    private String[] motorsName = new String[] {"AVD","AVG","ARD","ARG","ROULETTE","ASCG","ASCD"};
    private int nbreMoteurs = motorsName.length;
    private Servo ouv;
    public double posInit;


    //CONSTRUCTEUR

    HardwareInit(HardwareMap map)
    {
        this.map = map;

        Initialisation();
    }


    //INITIALISATION DES MOTEURS DANS L'ARRAY 'motors'

    private void Initialisation()
    {
        // Initialisation des moteurs DC placés dans un tableau
        for (String name : motorsName)
        {
            DcMotor motor = map.dcMotor.get(name);
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            motor.setDirection(DcMotor.Direction.FORWARD);
            motors.add(motor);
        }


        /*  Les deux moteurs de l'ascenseur sont montés dans des sens opposés
            il faut qu'il y en ait un qui tourne à l'envers
        */

        motors.get(ASCG).setDirection(DcMotor.Direction.REVERSE);

        // Pour moissoner dans le sens positif, on retourne aussi le moteur

        motors.get(MOISSONEUSE).setDirection(DcMotor.Direction.REVERSE);

        // *** Initialise les encodeurs des deux moteurs de l'ascenseur
        motors.get(ASCG).setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motors.get(ASCD).setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        /*  Pour synchroniser les moteurs, il suffit qu'ils soient en mode
            RUN_TO_POSITION puis de leur donner la même position-cible et
            la même vitesse.
         */

        motors.get(ASCD).setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motors.get(ASCG).setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // initialisation  de la position cible à 0 qui est la position actuelle
        motors.get(ASCD).setTargetPosition(0);
        motors.get(ASCG).setTargetPosition(0);

        // Initialisation du servo d'ouverture de porte

        ouv = map.servo.get("OUV");
        posInit = ouv.getPosition();




    }

    public  ArrayList<DcMotor> getMotors() {
        return motors;
    }

}
