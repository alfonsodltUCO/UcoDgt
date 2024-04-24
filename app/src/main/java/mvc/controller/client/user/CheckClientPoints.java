package mvc.controller.client.user;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import mvc.model.business.penalty.ManagerPenalty;
import mvc.model.business.penalty.PenaltyDTO;
import mvc.model.business.user.admin.AdminDTO;
import mvc.model.business.user.client.ClientDTO;
import mvc.model.business.user.client.ManagerClient;
import mvc.model.business.user.worker.WorkerDTO;
import mvc.model.data.PenaltyCallback;
import mvc.model.data.UserCallback;

public class CheckClientPoints extends AppCompatActivity {
    String dni;
    Date fechaExpedicion; // Fecha de expedición del carnet del usuario
    Date fechaUltimaInfraccion; // Fecha de la última infracción del usuario con pérdida de puntos
    int puntosActuales; // Puntos actuales del usuario

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
                //recibo user
                PenaltyDTO pnlt=new PenaltyDTO();
                pnlt.setDniClient(dni);
                mngP.getLastPenalty(pnlt,CheckClientPoints.this, new PenaltyCallback() {
                    @Override
                    public void onPenaltiesReceived(List<PenaltyDTO> penalties) {

                    }

                    @Override
                    public void onError(VolleyError error) {
                        //no perdida de puntos
                    }

                    @Override
                    public void onPenaltyReceived(PenaltyDTO penalty) {
                        puntosActuales = calcularPuntos(user.getDateLicenceObtaining(), penalty.getDate());
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

    private int calcularPuntos(Date fechaExpedicion, Date fechaUltimaInfraccion) {
        // Puntos iniciales
        int puntos = 8;

        // Obtener la fecha actual
        Date fechaActual = new Date();

        // Calcular el tiempo transcurrido desde la expedición del carnet en años
        long anosExperiencia = calcularAnosTranscurridos(fechaExpedicion, fechaActual);

        // Si el conductor tiene al menos 2 años de experiencia sin infracciones
        // aumentamos los puntos a 12
        if (anosExperiencia >= 2 && fechaUltimaInfraccion == null) {
            puntos = 12;
        }

        // Si el conductor tiene al menos 5 años de experiencia sin infracciones
        // aumentamos los puntos a 14
        if (anosExperiencia >= 5 && fechaUltimaInfraccion == null) {
            puntos = 14;
        }

        // Si el conductor tiene al menos 8 años de experiencia sin infracciones
        // aumentamos los puntos a 15
        if (anosExperiencia >= 8 && fechaUltimaInfraccion == null) {
            puntos = 15;
        }

        return puntos;
    }

    private long calcularAnosTranscurridos(Date fechaInicio, Date fechaFin) {
        long diferenciaEnMilisegundos = fechaFin.getTime() - fechaInicio.getTime();
        long anos = TimeUnit.DAYS.convert(diferenciaEnMilisegundos, TimeUnit.MILLISECONDS) / 365;
        return anos;
    }
}
