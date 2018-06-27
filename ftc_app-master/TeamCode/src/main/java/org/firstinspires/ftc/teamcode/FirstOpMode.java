package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by franc on 30/04/2017.
 */
@TeleOp(name="OPMODE_TEST", group="Practice-Bot")
public class FirstOpMode extends LinearOpMode{
    private DcMotor DROIT, GAUCHE;
    protected ElapsedTime period;
    @Override
    public void runOpMode() throws InterruptedException{
        double Left = 0.0, Right = 0.0, Power = 0.0;
        DROIT = hardwareMap.dcMotor.get("MOTEUR_DROIT");
        GAUCHE = hardwareMap.dcMotor.get("MOTEUR_GAUCHE");
        waitForStart();
        try {
            while (opModeIsActive())
            {
                Power = -gamepad1.left_stick_y;
                DROIT.setPower(Power);
                GAUCHE.setPower(Power);
                if(gamepad1.left_stick_x < 0)
                {
                    Right = Math.abs(gamepad1.left_stick_x);
                    Left = gamepad1.left_stick_x;
                }
                else
                {
                    Left = Math.abs(gamepad1.left_stick_x);
                    Right = gamepad1.left_stick_x;
                }
                DROIT.setPower(Right);
                GAUCHE.setPower(Left);
                waitForTick(40);
            }
        }catch (Exception e)
        {

        }
    }

    public void waitForTick(long periodMs) throws java.lang.InterruptedException {
        long remaining = periodMs - (long)period.milliseconds();
        // sleep for the remaining portion of the regular cycle period.
        if (remaining > 0) {
            Thread.sleep(remaining);
        }
        // Reset the cycle clock for the next pass.
        period.reset();
    }

}
