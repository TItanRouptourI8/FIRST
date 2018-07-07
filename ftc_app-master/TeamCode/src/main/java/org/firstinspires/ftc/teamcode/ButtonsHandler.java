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
    int bumperCount = 0; /*bumper2Count = 0*/;
    private boolean up,down,orange,vert,rouge,bumpG,bumpD,moissOn;
    private double alpha;
    private Telemetry telem;
   // private ArrayList<DcMotor> motors;
    private int encodeur;
    private int Tap = 0;
    private DcMotor ascG,ascD,moissoneuse;
    private int minAsc, maxSuspension,maxBenne;

    ButtonsHandler(HardwareInit robot, Gamepad gamepad1, Telemetry telem) {
        this.robot = robot;
        this.telem = telem;
        this.gamepad1 = gamepad1;
        thread = new Thread(this);
        this.minAsc = 0;
        this.maxSuspension = 1200;
        this.maxBenne = 350;
        this.moissOn = false;
        getValues();
        this.ascG = this.robot.getMotors().get(ASCG);
        this.ascD = this.robot.getMotors().get(ASCD);
        this.moissoneuse = this.robot.getMotors().get(MOISSONEUSE);
        thread.start();

    }

    private void getValues() {
        encodeur = robot.getMotors().get(5).getCurrentPosition();
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
        this.ascD.setPower(0.5);
        this.ascG.setPower(0.5);
        this.ascD.setTargetPosition(position);
        this.ascG.setTargetPosition(position);


    }



    @Override
    public void run() {

        int i = 0;
        while (!killed){

            //region Moissoneuse
            getValues();
            if (!bumpG && !bumpD)
            {
                bumperCount = 0;
            }


            if (this.bumpG && bumperCount < 1) {

                if (this.moissOn)
                    { this.moissoneuse.setPower(0);

                    }
                else
                    { this.moissoneuse.setPower(0.8);

                    }
                this.moissOn = !this.moissOn;
                bumperCount +=1;
            }

            else if (this.bumpD && bumperCount < 1) {
                if (this.moissOn) { this.moissoneuse.setPower(0); }
                else { this.moissoneuse.setPower(-0.8); }
                this.moissOn = !this.moissOn;
                bumperCount +=1;
            }
            //endregion



            // GESTION ASCENSEUR
            if (rouge) { this.arretAsc(); }
            else if (orange) {

                this.deplAsc(Math.min(750, encodeur + 150));


            }
            else if (vert || down) {this.deplAsc(this.minAsc); }
            else if (up) { this.deplAsc(this.maxSuspension); }

            //region Telemetry


            /*telem.addData("AVG", avg);
            telem.addData("AVD", avd);
            telem.addData("ARD", ard);
            telem.addData("ARG", arg);
            telem.addData("pression", trigger);*/
            /*i += 1;*/
            /*telem.addData("ButtonsHandler : boucle n° ",i);
            telem.update();*/

            //endregion

            Sleep(10);




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
