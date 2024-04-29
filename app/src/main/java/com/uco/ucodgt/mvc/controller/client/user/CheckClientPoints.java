package com.uco.ucodgt.mvc.controller.client.user;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

 import com.uco.ucodgt.mvc.model.business.penalty.ManagerPenalty;
import com.uco.ucodgt.mvc.model.business.penalty.PenaltyDTO;
import com.uco.ucodgt.mvc.model.business.user.admin.AdminDTO;
import com.uco.ucodgt.mvc.model.business.user.client.ClientDTO;
import com.uco.ucodgt.mvc.model.business.user.client.ManagerClient;
import com.uco.ucodgt.mvc.model.business.user.worker.WorkerDTO;
import com.uco.ucodgt.mvc.model.data.PenaltyCallback;
import com.uco.ucodgt.mvc.model.data.UserCallback;
import com.uco.ucodgt.mvc.view.client.ClientActivity;

/**
 * This class is responsible for checking the points of a client based on their driving history and updating the points accordingly.
 * @author Alfonso de la torre
 */
public class CheckClientPoints extends AppCompatActivity {
    String dni;

    int actualPoints;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        dni = getIntent().getStringExtra("dni");
        ManagerClient mngC=new ManagerClient();
        ManagerPenalty mngP=new ManagerPenalty();
        ClientDTO cl=new ClientDTO();
        cl.setDni(dni);

        mngC.getUser(cl,CheckClientPoints.this, new UserCallback() {
            @Override
            public void onUserReceived(ClientDTO user) {

                PenaltyDTO pnlt=new PenaltyDTO();
                pnlt.setDniClient(dni);
                mngP.getLastPenalty(pnlt,CheckClientPoints.this, new PenaltyCallback() {
                    @Override
                    public void onPenaltiesReceived(List<PenaltyDTO> penalties) {

                    }

                    @Override
                    public void onError(VolleyError error) {

                        actualPoints=calculatePoints(user.getDateLicenceObtaining(),null,user.getLicencepoints(),user.getDateLastUpdate());
                        user.setLicencepoints(actualPoints);
                        mngC.updatePoints(user,CheckClientPoints.this, new UserCallback() {
                            @Override
                            public void onUserReceived(ClientDTO user) {

                                Intent intentClient=new Intent(CheckClientPoints.this, ClientActivity.class);
                                intentClient.putExtra("dni",user.getDni());
                                startActivity(intentClient);
                                finish();
                            }

                            @Override
                            public void onError(VolleyError error) {
                                Intent intentClient=new Intent(CheckClientPoints.this, ClientActivity.class);
                                intentClient.putExtra("dni",user.getDni());
                                startActivity(intentClient);
                                finish();
                            }

                            @Override
                            public void onWorkerReceived(WorkerDTO user) {

                            }

                            @Override
                            public void onAdminReceived(AdminDTO user) {

                            }

                            @Override
                            public void onWorkersReceived(List<WorkerDTO> workers) {

                            }

                            @Override
                            public void onClientsReceived(List<ClientDTO> clients) {

                            }
                        });

                    }

                    @Override
                    public void onPenaltyReceived(PenaltyDTO penalty) {

                        actualPoints = calculatePoints(user.getDateLicenceObtaining(), penalty.getDate(),user.getLicencepoints(),user.getDateLastUpdate());
                        user.setLicencepoints(actualPoints);

                        mngC.updatePoints(user,CheckClientPoints.this, new UserCallback() {
                            @Override
                            public void onUserReceived(ClientDTO user) {

                                Intent intentClient=new Intent(CheckClientPoints.this, ClientActivity.class);
                                intentClient.putExtra("dni",user.getDni());
                                startActivity(intentClient);
                                finish();
                            }

                            @Override
                            public void onError(VolleyError error) {
                                Intent intentClient=new Intent(CheckClientPoints.this, ClientActivity.class);
                                intentClient.putExtra("dni",user.getDni());
                                startActivity(intentClient);
                                finish();
                            }

                            @Override
                            public void onWorkerReceived(WorkerDTO user) {

                            }

                            @Override
                            public void onAdminReceived(AdminDTO user) {

                            }

                            @Override
                            public void onWorkersReceived(List<WorkerDTO> workers) {

                            }

                            @Override
                            public void onClientsReceived(List<ClientDTO> clients) {

                            }
                        });

                    }
                });
            }

            @Override
            public void onError(VolleyError error) {

            }

            @Override
            public void onWorkerReceived(WorkerDTO user) {

            }

            @Override
            public void onAdminReceived(AdminDTO user) {

            }

            @Override
            public void onWorkersReceived(List<WorkerDTO> workers) {

            }

            @Override
            public void onClientsReceived(List<ClientDTO> clients) {

            }
        });

    }

    /**
     * Calculates the points of the client based on their driving history and updates the points accordingly.
     *
     * @param obtaining The date when the client obtained their driving license.
     * @param lastPenalty   The date of the client's last infraction, if any.
     * @param actualpoints  The current points of the client.
     * @param lastUpdate    The date of the last update to the client's points.
     * @return  The updated points of the client.
     */
    private int calculatePoints(Date obtaining, Date lastPenalty, int actualpoints,Date lastUpdate) {
        int points = actualpoints;

        Date currentDate = new Date();
        long yearsExperience = calculateYears(obtaining, currentDate);
        long yearsLastUpdate=calculateYears(lastUpdate,currentDate);

        if(yearsLastUpdate>=1){//Each 1 year check points
            if(lastPenalty!=null){//Receive a penalty
                long yearsWithoutPenalties=calculateYears(lastPenalty,new Date());

                if(yearsExperience<=3){//Novel
                   if(yearsWithoutPenalties>=2){
                       points+=2;
                   }
                }else{//Not novel
                    if(yearsWithoutPenalties>=3 && yearsLastUpdate>=3){
                        if(points<=12){
                            points+=2;
                        }else{
                            if(yearsExperience>=6 && yearsLastUpdate>=6){
                                points+=1;
                            }
                        }
                    }
                }
            }else{//Never receive a penalty

                if(yearsExperience<=3){//Novel
                    if(points==8){
                        if(yearsExperience>=2){
                            points=12;
                        }
                    }
                }else{//Not novel
                    if(yearsLastUpdate>=3){
                        if(points<14){
                            points+=2;
                        }else{
                            if(yearsExperience>=6 && (yearsLastUpdate)>=6){
                                points+=1;
                            }
                        }
                    }
                }
            }
        }

        if (points > 15) {

            points = 15;
        }

        return points;
    }


    /**
     * Calculates the number of years between two dates.
     *
     * @param startDate The start date.
     * @param endDate   The end date.
     * @return          The number of years between the start and end dates.
     */
    private long calculateYears(Date startDate, Date endDate) {

        long differenceInMillis = endDate.getTime() - startDate.getTime();
        return TimeUnit.DAYS.convert(differenceInMillis, TimeUnit.MILLISECONDS) / 365;
    }

}
