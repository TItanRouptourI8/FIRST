package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Gamepad;

public class AscenseurHandler implements Runnable {

    HardwareInit robot;
    Gamepad gamepad2;
    private float power = 0.4f;
    float L1y;

    public AscenseurHandler(Gamepad gamepad, HardwareInit robot)
    {
    this.robot = robot;
    gamepad2 = gamepad;
    }


    private void getValues()
    {
    L1y = gamepad2.left_stick_y;
    }


    @Override
    public void run() {
        getValues();
        if (L1y != 0)
        {
            if (L1y > 0)
            {
                //robot.Ascd().setPower(power);
               // robot.Ascg().setPower(power);
            }
            else
            {
                //robot.Ascd().setPower(-power);
               // robot.Ascg().setPower(-power);
            }
        }
        else
        {
            //robot.Ascd().setPower(0);
            //robot.Ascg().setPower(0);
        }


    }
}
