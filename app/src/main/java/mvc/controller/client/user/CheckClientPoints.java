package mvc.controller.client.user;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import mvc.controller.CheckLogIn;
import mvc.model.business.penalty.ManagerPenalty;
import mvc.model.business.penalty.PenaltyDTO;
import mvc.model.business.user.admin.AdminDTO;
import mvc.model.business.user.client.ClientDTO;
import mvc.model.business.user.client.ManagerClient;
import mvc.model.business.user.worker.WorkerDTO;
import mvc.model.data.PenaltyCallback;
import mvc.model.data.UserCallback;
import mvc.view.client.ClientActivity;

public class CheckClientPoints extends AppCompatActivity {
    String dni;
    Date fechaExpedicion;
    Date fechaUltimaInfraccion;
    int actualPoints;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Log.d("d","1");
        dni = getIntent().getStringExtra("dni");
        ManagerClient mngC=new ManagerClient();
        ManagerPenalty mngP=new ManagerPenalty();
        ClientDTO cl=new ClientDTO();
        cl.setDni(dni);

        mngC.getUser(cl,CheckClientPoints.this, new UserCallback() {
            @Override
            public void onUserReceived(ClientDTO user) {
                //recibo user
                Log.d("d","2");

                PenaltyDTO pnlt=new PenaltyDTO();
                pnlt.setDniClient(dni);
                mngP.getLastPenalty(pnlt,CheckClientPoints.this, new PenaltyCallback() {
                    @Override
                    public void onPenaltiesReceived(List<PenaltyDTO> penalties) {

                    }

                    @Override
                    public void onError(VolleyError error) {

                        actualPoints=calculatePoints(user.getDateLicenceObtaining(),null,user.getLicencepoints());
                        user.setLicencepoints(actualPoints);
                        mngC.updatePoints(user,CheckClientPoints.this, new UserCallback() {
                            @Override
                            public void onUserReceived(ClientDTO user) {
                                Log.d("d","3");

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
                        Log.d("d","4");

                        actualPoints = calculatePoints(user.getDateLicenceObtaining(), penalty.getDate(),user.getLicencepoints());
                        user.setLicencepoints(actualPoints);
                        mngC.updatePoints(user,CheckClientPoints.this, new UserCallback() {
                            @Override
                            public void onUserReceived(ClientDTO user) {
                                Log.d("d","5");

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
    private int calculatePoints(Date fechaExpedicion, Date fechaUltimaInfraccion, int puntosActuales) {
        int points = puntosActuales;

        Date currentDate = new Date();
        long yearsExperience = calculateYears(fechaExpedicion, currentDate);




        // Verificar si el conductor tiene 8 puntos iniciales y ha pasado al menos 3 años desde la expedición del permiso
        // Además, verificar si no ha cometido infracciones en los últimos 2 años
        if (puntosActuales == 8 && yearsExperience >= 3 && fechaUltimaInfraccion == null) {
            long yearsWithoutInfringement = calculateYears(fechaExpedicion, currentDate);
            if (yearsWithoutInfringement >= 2) { // Se cambió a 2 años para cumplir con el requisito
                points = 12;
            }
        }else{
            if(fechaUltimaInfraccion!=null) {

                long yearsWithoutInfringement = calculateYears(fechaUltimaInfraccion, currentDate);

                if (yearsExperience >= 3 && yearsWithoutInfringement >= 3) {

                    points += 2;

                    if (yearsWithoutInfringement >= 6) {

                        points += 1;

                    }
                }
            }
        }

        long yearsWithoutInfringement = calculateYears(fechaExpedicion, currentDate);

        if (fechaUltimaInfraccion == null && yearsExperience >= 3 && yearsWithoutInfringement >= 3) {

            points += 2;

            if (yearsWithoutInfringement >= 6) {

                points += 1;

            }

        }


        if (points > 15) {

            points = 15;
        }

        return points;
    }



    private long calculateYears(Date startDate, Date endDate) {

        long differenceInMillis = endDate.getTime() - startDate.getTime();
        long years = TimeUnit.DAYS.convert(differenceInMillis, TimeUnit.MILLISECONDS) / 365;
        return years;
    }

}
