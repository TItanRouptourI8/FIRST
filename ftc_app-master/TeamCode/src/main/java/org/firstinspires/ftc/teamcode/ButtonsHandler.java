package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import static java.lang.Thread.sleep;
import static org.firstinspires.ftc.teamcode.HardwareInit.ASCD;
import static org.firstinspires.ftc.teamcode.HardwareInit.ASCG;
import static org.firstinspires.ftc.teamcode.HardwareInit.MOISSONEUSE;
public class ButtonsHandler implements Runnable {

    private HardwareInit robot;
    private Gamepad gamepad1;
    private Thread thread;
    private boolean killed = false;
    private boolean up,down,orange,vert,rouge,bumpG,bumpD,moissOn;
    private double alpha;
    private Telemetry telem;
   // private ArrayList<DcMotor> motors;
    private DcMotor ascG,ascD,moissoneuse;
    private int minAsc, maxSuspension,maxBenne;

    ButtonsHandler(HardwareInit robot, Gamepad gamepad1, Telemetry telem) {
        this.robot = robot;
        this.telem = telem;
        this.gamepad1 = gamepad1;
        thread = new Thread(this);
        this.minAsc = 0;
        this.maxSuspension = 700;
        this.maxBenne = 350;
        this.moissOn = false;
        getValues();
        this.ascG = this.robot.getMotors().get(ASCG);
        this.ascD = this.robot.getMotors().get(ASCD);
        this.moissoneuse = this.robot.getMotors().get(MOISSONEUSE);
        thread.start();

    }

    private void getValues() {
        this.up = gamepad1.dpad_up;
        this.down = gamepad1.dpad_down;
        this.orange = gamepad1.y;
        this.vert = gamepad1.a;
        this.rouge = gamepad1.b;
        this.bumpG = gamepad1.left_bumper;
        this.bumpD = gamepad1.right_bumper;

    }

    public void kill(){
        this.ascG.setPower(0);
        this.ascD.setPower(0);
        this.moissoneuse.setPower(0);
        this.killed = true;
    }

    private void arretAsc(){
        this.ascG.setPower(0);
        this.ascD.setPower(0);
    }

    private void deplAsc(int position){
        this.ascG.setTargetPosition(this.ascG.getCurrentPosition());
        this.ascG.setPower(0.3);
        this.ascD.setTargetPosition(position);
        this.ascD.setPower(0.3);
        while (this.ascD.isBusy()) {
            this.ascG.setTargetPosition(this.ascD.getCurrentPosition());
        }
    }



    @Override
    public void run() {


        while (!killed){
            getValues();
            if (this.bumpG) {
                if (this.moissOn) { this.moissoneuse.setPower(0);}
                else { this.moissoneuse.setPower(1); }
            }
            if (this.bumpD) {
                if (this.moissOn) { this.moissoneuse.setPower(0);}
                else { this.moissoneuse.setPower(-1); }
            }
            if (rouge) { this.arretAsc(); }
            else {
                if (orange) {
                    this.deplAsc(this.maxBenne);
                } else {
                    if (vert || down) {
                        this.deplAsc(this.minAsc);
                    } else {
                        if (up) {
                            this.deplAsc(this.maxSuspension);
                        }
                    }
                }
            }



            //region Telemetry


            /*telem.addData("AVG", avg);
            telem.addData("AVD", avd);
            telem.addData("ARD", ard);
            telem.addData("ARG", arg);
            telem.addData("pression", trigger);
            telem.update();*/

            //endregion

            Sleep(10);
            //endregion



        }
    }

private void Sleep(long time)
{
    try
    {
        sleep(time);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
}

}
