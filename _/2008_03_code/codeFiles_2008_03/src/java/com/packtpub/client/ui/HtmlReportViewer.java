/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.packtpub.client.ui;

import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Frame;
import com.packtpub.client.rpc.GWTService;
import com.packtpub.client.rpc.GWTServiceAsync;
import java.util.HashMap;

/**
 *
 * @author Shamsuddin
 */
class HtmlReportViewer extends FormPanel
{

    public HtmlReportViewer(String fileName, HashMap<String, Integer> param, String title)
    {

        setLayout(new FitLayout());
        setHeading(title);

        final Frame frame = new Frame();

        add(frame);

        AsyncCallback<String> reportURLCallback = new AsyncCallback<String>()
        {

            MessageBox messageBox = new MessageBox();

            @Override
            public void onFailure(Throwable caught)
            {

                messageBox.setMessage("Cannot load the report. \nCannot connect to remote resource");
                messageBox.show();
                caught.printStackTrace();
            }

            @Override
            public void onSuccess(String result)
            {
                if (result != null)
                {

                    frame.setUrl(result);

                } else
                {
                    messageBox.setMessage("Cannot load the report");
                    messageBox.show();

                }
            }
        };

        ((GWTServiceAsync) GWT.create(GWTService.class)).getHtmlReport(fileName, param, reportURLCallback);


    }
}
