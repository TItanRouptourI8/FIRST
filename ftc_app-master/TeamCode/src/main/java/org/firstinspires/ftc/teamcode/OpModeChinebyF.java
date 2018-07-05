package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@TeleOp(name="Holonomic drive by F", group="WR")
public class OpModeChinebyF extends LinearOpMode {
    private HardwareInit hardwareInit;
    private ArrayList<DcMotor> motors;
    private MovesHandler movesHandler;
    private AscenseurHandler ascenseurHandler;
    private ThreadPool pool;


    @Override
    public void runOpMode() throws InterruptedException {
        hardwareInit = new HardwareInit(hardwareMap);
        motors = hardwareInit.getMotors();
        telemetry.addData("Available Processors",Runtime.getRuntime().availableProcessors() );
        waitForStart();
        movesHandler = new MovesHandler(hardwareInit,gamepad1, telemetry);
        ascenseurHandler = new AscenseurHandler(gamepad2, hardwareInit);
        pool = new ThreadPool(movesHandler,ascenseurHandler);

        while (opModeIsActive())
        {


//            motors.get(AVD).setPower(0.2);
//            motors.get(AVG).setPower(0.2);
//            motors.get(ARD).setPower(0.2);
//            motors.get(ARG).setPower(0.2);
//            motors.get(MOISSONEUSE).setPower(0.4);


           /* telemetry.addLine("BONSOIR");

            telemetry.update();*/
        }
        pool.killed = true;
    }
}
