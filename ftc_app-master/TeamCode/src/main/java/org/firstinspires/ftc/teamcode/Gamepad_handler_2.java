package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

//import static org.firstinspires.ftc.teamcode.WeRobotJ_mainV1_Linear.rtPosCible;


public class Gamepad_handler_2 implements Runnable {
    double initSortieO = 0.3;
    double initSortieB = 0.4;
    protected double /*R1x, R1y,*/ L1y, L1x;
    private HardwareWeRobotJ robot;
    private Gamepad gamepad1;
    private boolean killed = false;

    public Gamepad_handler_2(HardwareWeRobotJ robot, Gamepad gamepad1) {
        Thread thread = new Thread(this);
        this.robot = robot;
        this.gamepad1 = gamepad1;
        initGamepad();
        thread.start();
    }
    public void initGamepad()
    {
        /*R1x = gamepad1.right_stick_x;
        R1y = -gamepad1.right_stick_y;*/
        L1y = gamepad1.left_stick_y;
        L1x = gamepad1.left_stick_x;
    }
/////////////////////////////FONCTION MOUVEMENTS ROBOTS////////////////////

    /*protected void avance(double vitesse){
        //robot.montePanier.setPower(0.1);
        robot.AvDMoteur.setPower(vitesse);
        robot.AvGMoteur.setPower(vitesse);
        robot.ArDMoteur.setPower(vitesse);
        robot.ArGMoteur.setPower(vitesse);
    }

    protected void tourne(double vitesse){
        //robot.montePanier.setPower(0.1);
        double v = 0.9*vitesse;
        robot.AvDMoteur.setPower(-v);
        robot.AvGMoteur.setPower(v);
        robot.ArDMoteur.setPower(-v);
        robot.ArGMoteur.setPower(v);
    }*/

    protected void elevePanier(int pos)
    {
        robot.montePanier.setPower(0);
        robot.montePanier.setTargetPosition(pos-40);
        robot.montePanier.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.montePanier.setPower(1);
        while (robot.montePanier.isBusy()) {
            if (! WeRobotJ_mainV1_Linear.opModActif){break;}
        }
        robot.montePanier.setTargetPosition(pos);
        robot.montePanier.setPower(0.4);
        while (robot.montePanier.isBusy()) {
            if (!WeRobotJ_mainV1_Linear.opModActif) {
                break;
            }
        }
        //robot.montePanier.setPower(1);
        robot.montePanier.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        /*robot.montePanier.setMode(DcMotor.RunMode.RUN_USING_ENCODER);*/
    }

    protected void descendPanier(int pos){
        robot.montePanier.setPower(0);
       // robot.montePanier.setTargetPosition(pos+70);
        robot.montePanier.setTargetPosition(pos+30);
        robot.montePanier.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.montePanier.setPower(0.4);
        while (robot.montePanier.getCurrentPosition()>pos+30) {
            if (!WeRobotJ_mainV1_Linear.opModActif){break;}
        }
        robot.montePanier.setPower(0.2);
        robot.montePanier.setTargetPosition(pos);
        while (robot.montePanier.isBusy()) {
            if (!WeRobotJ_mainV1_Linear.opModActif){break;}
        }
        //robot.montePanier.setPower(1);
        /*robot.montePanier.setPower(-0.2);
        robot.montePanier.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        while (robot.montePanier.getCurrentPosition()>-90){
            if (!WeRobotJ_mainV1_Linear.opModActif) {
                break;
            }
        }*/
        robot.montePanier.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


    }

    protected void gestionPanier() {
        //robot.montePanier.setPower(0);
        if (gamepad1.dpad_up) {
            robot.verrouPanier.setPosition(0);
            this.elevePanier(0);
            robot.verrouPanier.setPosition(1);
        } else if (gamepad1.dpad_down) {
            robot.verrouPanier.setPosition(0);
            this.descendPanier(-97);
        }
    }
    /*protected double convertPowerToCurve(double input){
        // return Range.clip(0.7*Math.pow(input, 3.0) + 0.4*input, -1.0, 1.0);
        return 0.3*0.5*Math.pow(input,3.0)+0.3*0.5*Math.pow(input,5.0)+input/Math.abs(input)*0.2;
    }*/

    ///////////////////////// MENU INTERMEDIAIRE //////////////////



    protected  void choixModeTri(){
        boolean choix = false;
        WeRobotJ_mainV1_Linear.menuChoix=true;
        WeRobotJ_mainV1_Linear.telemetryProxy.addLine("**** CHOIX DU MODE DE TRI ****");
        WeRobotJ_mainV1_Linear.telemetryProxy.addLine(" Bouton X : GAUCHE");
        WeRobotJ_mainV1_Linear.telemetryProxy.addLine(" Bouton B : DROITE");
        WeRobotJ_mainV1_Linear.telemetryProxy.addLine(" Bouton Y : UNE SEULE COULEUR");
        WeRobotJ_mainV1_Linear.telemetryProxy.addLine(" Bouton A : MANUEL");
        WeRobotJ_mainV1_Linear.telemetryProxy.addLine(" CHOIX ? .........");
        WeRobotJ_mainV1_Linear.telemetryProxy.update();
        while (!choix){
            if (gamepad1.x){
                WeRobotJ_mainV1_Linear.modeTri = ModeTri.GAUCHE;
                choix = true;
            }
            if (gamepad1.b){
                WeRobotJ_mainV1_Linear.modeTri = ModeTri.DROITE;
                choix = true;
            }
            if (gamepad1.y){
                WeRobotJ_mainV1_Linear.modeTri = ModeTri.UNI;
                choix = true;
            }
            if (gamepad1.a){
                WeRobotJ_mainV1_Linear.modeTri = ModeTri.MANUEL;
                choix = true;
            }
        }
        WeRobotJ_mainV1_Linear.menuChoix = false;

    }

    /////////////////////////BOUCLE PRINCIPALE/////////////////////




    @Override
    public void run() {
        while(!killed) {
            initGamepad();
            if (gamepad1.right_bumper) {
                choixModeTri();
            }

            if (gamepad1.left_bumper) {
                WeRobotJ_mainV1_Linear.debug = !WeRobotJ_mainV1_Linear.debug;
            }

            /*if ((R1y > Math.abs(R1x)) | (-R1y > Math.abs(R1x))) {
                avance(convertPowerToCurve(R1y));
            } else {
                tourne(convertPowerToCurve(R1x));
            }*/

            if (Math.abs(L1y) > 0.2) {
                robot.Hanger.setPower(L1y / Math.abs(L1y) * 0.5);
            } else {
                robot.Hanger.setPower(0);
            }

            if (Math.abs(L1x) > 0.2) {

                WeRobotJ_mainV1_Linear.rtPosCible -= L1x / Math.abs(L1x) * 96;
                robot.roueTri.setTargetPosition(WeRobotJ_mainV1_Linear.rtPosCible);
                robot.waitForTick(200);
            }

            if (gamepad1.dpad_left){
                WeRobotJ_mainV1_Linear.rtPosCible +=  3;
                robot.roueTri.setTargetPosition(WeRobotJ_mainV1_Linear.rtPosCible);
                robot.waitForTick(50);
            }
            if (gamepad1.dpad_right){
                WeRobotJ_mainV1_Linear.rtPosCible -=  3;
                robot.roueTri.setTargetPosition(WeRobotJ_mainV1_Linear.rtPosCible);
                robot.waitForTick(50);
            }

            gestionPanier();

            if (gamepad1.y) {
                if(WeRobotJ_mainV1_Linear.modeTri == ModeTri.DROITE || WeRobotJ_mainV1_Linear.modeTri == ModeTri.UNI) {
                    robot.sortieOranges.setPosition(initSortieO + .25);
                    robot.waitForTick(500);
                    robot.sortieOranges.setPosition(initSortieO);
                }
                else if (WeRobotJ_mainV1_Linear.modeTri == ModeTri.GAUCHE) {
                    robot.sortieBleues.setPosition(initSortieB - .25);
                    robot.waitForTick(500);
                    robot.sortieBleues.setPosition(initSortieB);
                }
            }

            if (gamepad1.x) {
                if (WeRobotJ_mainV1_Linear.modeTri == ModeTri.DROITE || WeRobotJ_mainV1_Linear.modeTri == ModeTri.UNI) {
                    robot.sortieBleues.setPosition(initSortieB - .25);
                    robot.waitForTick(500);
                    robot.sortieBleues.setPosition(initSortieB);
                }
                else if(WeRobotJ_mainV1_Linear.modeTri == ModeTri.GAUCHE ){
                    robot.sortieOranges.setPosition(initSortieO + .25);
                    robot.waitForTick(500);
                    robot.sortieOranges.setPosition(initSortieO);
                }
            }

            if (gamepad1.b){ robot.verrouPanier.setPosition(1);}
            if (gamepad1.a){ robot.verrouPanier.setPosition(0);}
            sleep(10);

        }

    }
    void kill(){
        killed = true;
        stopMotors();
        WeRobotJ_mainV1_Linear.telemetryProxy.addLine("GamePad thread has been killed!");
        WeRobotJ_mainV1_Linear.telemetryProxy.update();
    }

    void stopMotors(){
        robot.AvGMoteur.setPower(0);
        robot.ArGMoteur.setPower(0);
        robot.AvDMoteur.setPower(0);
        robot.ArDMoteur.setPower(0);
        robot.Hanger.setPower(0);
        robot.montePanier.setPower(0);
        robot.roueTri.setPower(0);
    }

    private void sleep(int t){
        try {
            Thread.sleep(t);
        } catch (InterruptedException e) {
            WeRobotJ_mainV1_Linear.menuChoix=true;
            WeRobotJ_mainV1_Linear.telemetryProxy.addData("Interrupted Exception occured", "Line %d", e.getStackTrace()[0].getLineNumber());
            WeRobotJ_mainV1_Linear.telemetryProxy.update();

        }
    }
}
