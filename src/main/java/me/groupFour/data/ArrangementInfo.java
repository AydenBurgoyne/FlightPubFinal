package me.groupFour.data;

public class ArrangementInfo {
    //variables
    private Integer[] Arrangement;
    private int Rows;
    private String Excluded;
    private Integer offset;

    public Integer[] getArrangement() {
        return Arrangement;
    }

    public void setArrangement(Integer[] arrangement) {
        Arrangement = arrangement;
    }

    public int getRows() {
        return Rows;
    }

    public void setRows(int rows) {
        Rows = rows;
    }

    public String getExcluded() {
        return Excluded;
    }

    public void setExcluded(String excluded) {
        Excluded = excluded;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }
}
