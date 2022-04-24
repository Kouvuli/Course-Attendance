package Models;

import java.util.Date;

public class AttendanceResultRow {
    private int id;
    private String scheduleName;
    private String date;
    private String dayOfWeek;
    private String shiftStart;
    private String shiftEnd;
    private String status;
    public AttendanceResultRow(){

    }

    public AttendanceResultRow(int id,String scheduleName, String date, String dayOfWeek, String shiftStart, String shiftEnd, String status) {
        this.id=id;
        this.scheduleName = scheduleName;
        this.date = date;
        this.dayOfWeek = dayOfWeek;
        this.shiftStart = shiftStart;
        this.shiftEnd = shiftEnd;
        this.status = status;
    }

    public void setScheduleName(String scheduleName) {
        this.scheduleName = scheduleName;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public void setShiftStart(String shiftStart) {
        this.shiftStart = shiftStart;
    }

    public void setShiftEnd(String shiftEnd) {
        this.shiftEnd = shiftEnd;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getScheduleName() {
        return scheduleName;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public String getShiftStart() {
        return shiftStart;
    }

    public String getShiftEnd() {
        return shiftEnd;
    }

    public String getStatus() {
        return status;
    }
}
