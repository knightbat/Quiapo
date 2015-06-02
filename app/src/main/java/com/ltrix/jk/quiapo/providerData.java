package com.ltrix.jk.quiapo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jk on 4/1/15.
 */
public class providerData implements Serializable{


    private List<ProviderElement> providerElementList = new ArrayList<>();
//    private List<ProviderElement> providerElementList2 = new ArrayList<>();
//    private List<ProviderElement> providerElementList3 = new ArrayList<>();
//    private List<ProviderElement> providerElementList4 = new ArrayList<>();
//    private List<ProviderElement> providerElementList5 = new ArrayList<>();
//    private List<ProviderElement> providerElementList6 = new ArrayList<>();





    public providerData(){

        providerElementList.add(new ProviderElement("sawa","main",0));
        providerElementList.add(new ProviderElement("sawa","10",0));
        providerElementList.add(new ProviderElement("sawa","20",0));
        providerElementList.add(new ProviderElement("sawa","50",0));
        providerElementList.add(new ProviderElement("sawa","100",0));
        providerElementList.add(new ProviderElement("sawa","300",0));
        providerElementList.add(new ProviderElement("sawa","10p",0));
        providerElementList.add(new ProviderElement("sawa","20p",0));
        providerElementList.add(new ProviderElement("sawa", "300p", 0));

        providerElementList.add(new ProviderElement("mobily","main",0));
        providerElementList.add(new ProviderElement("mobily","10",0));
        providerElementList.add(new ProviderElement("mobily","30",0));
        providerElementList.add(new ProviderElement("mobily","50",0));
        providerElementList.add(new ProviderElement("mobily","100",0));
        providerElementList.add(new ProviderElement("mobily","10p",0));
        providerElementList.add(new ProviderElement("mobily","30p",0));
        providerElementList.add(new ProviderElement("mobily","50p",0));
        providerElementList.add(new ProviderElement("mobily", "100p", 0));

        providerElementList.add(new ProviderElement("zain","main",0));
        providerElementList.add(new ProviderElement("zain","10",0));
        providerElementList.add(new ProviderElement("zain","20",0));
        providerElementList.add(new ProviderElement("zain","50",0));
        providerElementList.add(new ProviderElement("zain","100",0));
        providerElementList.add(new ProviderElement("zain","10p",0));
        providerElementList.add(new ProviderElement("zain", "20p", 0));

        providerElementList.add(new ProviderElement("marhaba", "main", 0));
        providerElementList.add(new ProviderElement("marhaba", "10", 0));

        providerElementList.add(new ProviderElement("friendi","main",0));
        providerElementList.add(new ProviderElement("friendi","10",0));
        providerElementList.add(new ProviderElement("friendi", "20", 0));

        providerElementList.add(new ProviderElement("lebara","main",0));
        providerElementList.add(new ProviderElement("lebara","10",0));
        providerElementList.add(new ProviderElement("lebara", "20", 0));

    }

    public List<ProviderElement> getProviders(){

        return providerElementList;
    }
}
