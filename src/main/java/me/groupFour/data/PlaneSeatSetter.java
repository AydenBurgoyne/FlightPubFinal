package me.groupFour.data;

import org.json.JSONArray;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

//includes all the stuff to set the plane seating, takes in the model view adds the stuff then returns it.
public class PlaneSeatSetter {
    //variables:
    //constructor:


    //methods
    public ModelAndView ConfiguringMap(ModelAndView view, SeatingMapObj seatingMap, ModelMap model) {
        int offset;
        FlightEntity temp = seatingMap.getFlight();
        PlaneSeatingArrangementsEntity config = temp.getPlaneCode().getPlaneList().get(0);

        //variables that store the seat info.

        List<LegEntity> list = new LinkedList<LegEntity>();
        ClassEntity classCode = seatingMap.getClasscode();

        ArrangementInfo arInfo = seatingInfo(classCode, config);
        list = seatingMap.getLegEntityDAO().getLegs(classCode, temp);
        JSONArray jsonArrangements = new JSONArray();

        for(Integer arrangement : arInfo.getArrangement()){
            jsonArrangements.put(arrangement);
        }
        view.addObject("Arrangement", jsonArrangements.toString());
        view.addObject("Rows", arInfo.getRows());
        view.addObject("Excluded", arInfo.getExcluded());
        view.addObject("offset", arInfo.getOffset());

        List<String> BookedList = SeatArray(list);
        view.addObject("BookedList", BookedList);
        PriceEntity price = seatingMap.getPrice();
        view.addObject("currentFlight", temp);
        model.put("proposedFlights", seatingMap.getProposedFlights());
        model.put("price", price);
        view.addObject("PlaneConfig", config);
        view.addObject("price", price);
        return view;
    }

    private Integer[] changeArray(String[] array) {
        Integer[] arrayInt = new Integer[array.length];

        for (int i = 0; i < array.length; i++) {
            arrayInt[i] = Integer.valueOf(array[i]);
        }

        return arrayInt;
    }


    private String fixingString(String[] array) {
        StringBuilder output = new StringBuilder("[");
        for (int i = 0; i < array.length; i++) {
            output.append("'").append(array[i]).append("'");
            if (i != array.length - 1) {
                output.append(",");
            }
        }
        output.append("]");
        return output.toString();
    }


    private String fixingString(List<Integer> array) {
        StringBuilder output = new StringBuilder();
        Iterator<Integer> it = array.iterator();
        while (it.hasNext()) {
            output.append(it.next());
            if (it.hasNext()) {
                output.append(",");
            }
        }

        return output.toString();
    }

    public List<String> SeatArray(List<LegEntity> list) {
        Iterator<LegEntity> it = list.iterator();
        List<String> newList = new LinkedList<String>();
        while (it.hasNext()) {
            String temp = "\'";
            temp = temp+it.next().getSeat();
            temp= temp+"\'";
            newList.add(temp);
        }
        return newList;
    }

    public Integer gettingMatrixLength(Integer[] Arrangement) {
        Integer mLength = 0;
        for (int i = 0; i < Arrangement.length; i++) {
            mLength += Arrangement[i];
        }
        return mLength;
    }

    public ArrangementInfo seatingInfo(ClassEntity classcode, PlaneSeatingArrangementsEntity config) {
        ArrangementInfo newInfo = new ArrangementInfo();
        if (classcode.getClassCode().equals("BUS")) {
            newInfo.setArrangement(changeArray(config.getBC_arrangement().split(",")));
            newInfo.setRows(config.getBC_row_count());
            newInfo.setExcluded(fixingString(config.getBC_seats_excluded().split(",")));
            newInfo.setOffset(Integer.parseInt(config.getBC_row_range().split(" ", 2)[0]));
        } else if (classcode.getClassCode().equals("ECO")) {
            newInfo.setArrangement(changeArray(config.getEE_arrangement().split(",")));
            newInfo.setRows(config.getEE_row_count());
            newInfo.setExcluded(fixingString(config.getEE_seats_excluded().split(",")));
            newInfo.setOffset(Integer.parseInt(config.getEE_row_range().split(" ", 2)[0]));
        } else if (classcode.getClassCode().equals("FIR")) {
            newInfo.setArrangement(changeArray(config.getFC_arrangement().split(",")));
            newInfo.setRows(config.getFC_row_count());
            newInfo.setExcluded(fixingString(config.getFC_seats_excluded().split(",")));
            newInfo.setOffset(Integer.parseInt(config.getFC_row_range().split(" ", 2)[0]));
            newInfo.setOffset(newInfo.getOffset() + 1);
        } else if (classcode.getClassCode().equals("PME")) {
            newInfo.setArrangement(changeArray(config.getPE_arrangement().split(",")));
            newInfo.setRows(config.getPE_row_count());
            newInfo.setExcluded(fixingString(config.getPE_seats_excluded().split(",")));
            newInfo.setOffset(Integer.parseInt(config.getPE_row_range().split(" ", 2)[0]));
        }
        return newInfo;
    }
}



