/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.russianpost.siberia;

import java.net.URI;
import java.util.logging.Logger;
import net.sf.corn.httpclient.HttpClient;
import net.sf.corn.httpclient.HttpResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import ru.russianpost.siberia.dataaccess.Ticket;

/**
 *
 * @author Andrey.Isakov
 */
public class JSONTicketDetail {

    public static void getTicketDetailData(Ticket tk) {
        String http = "http://int.reports.pochta.ru/easops/api/Doc//";
        try {
            HttpClient client = new HttpClient(new URI(http + tk.getBarcode()));
            HttpResponse response = client.sendData(HttpClient.HTTP_METHOD.GET);
            if (!response.hasError()) {
                String jsonString = response.getData();
                JSONObject obj = new JSONObject(jsonString);
                JSONArray links_list = obj.getJSONArray("links_list");
                JSONObject sys_id = obj.getJSONObject("sys_id");
                JSONArray rpo_list = obj.getJSONArray("rpo_list");
                JSONObject base = rpo_list.getJSONObject(0);
                JSONObject base_info = base.getJSONObject("base_info");
                JSONObject sys_id1 = base_info.getJSONObject("sys_id");
                tk.setPo_version(sys_id1.getString("po_version") + " " + sys_id1.getString("source_id"));
                JSONObject rpo_info = base.getJSONObject("rpo_info");
                JSONObject rcpn = rpo_info.getJSONObject("rcpn");
                tk.setRecp_name(rcpn.getString("name"));
                JSONObject rcpn_address = rpo_info.getJSONObject("rcpn_address");
                tk.setRecp_index(rcpn_address.getString("index"));
                JSONObject raddress = rcpn_address.getJSONObject("address");
                tk.setRecp_place(raddress.getString("place"));
                tk.setRecp_region(raddress.getString("region"));
                tk.setRecp_street(raddress.getString("street"));
                tk.setRecp_area(raddress.getString("area"));

                JSONObject sndr = rpo_info.getJSONObject("sndr");
                tk.setSender_name(sndr.getString("name"));
                JSONObject sndr_address = rpo_info.getJSONObject("sndr_address");
                tk.setSender_index(sndr_address.getString("index"));
                JSONObject saddress = sndr_address.getJSONObject("address");
                tk.setSender_region(saddress.getString("region"));
                tk.setSender_area(saddress.getString("area"));
                tk.setSender_place(saddress.getString("place"));
                tk.setSender_street(saddress.getString("street"));
                JSONObject shouse = saddress.getJSONObject("house");
                tk.setSender_value(shouse.getString("value"));
            }
        } catch (Exception ex) {
            LOG.severe(ex.getMessage());
        }
    }
    private static final Logger LOG = Logger.getLogger(GetTicketSession.class.getName());
}
