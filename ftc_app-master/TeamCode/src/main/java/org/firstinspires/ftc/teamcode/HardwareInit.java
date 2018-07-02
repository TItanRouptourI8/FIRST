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
    static int AVD = 0, AVG = 1, ARD = 2,ARG = 3, OUV = 4, MOISSONEUSE = 4;

    private String[] motorsName = new String[] {"AVD","AVG","ARD","ARG","ROULETTE"};
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


    }

    public  ArrayList<DcMotor> getMotors() {
        return motors;
    }

}
