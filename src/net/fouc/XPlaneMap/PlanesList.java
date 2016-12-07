package net.fouc.XPlaneMap;

import java.net.InetAddress;
import java.util.HashMap;

public class PlanesList {
  private HashMap<String, Float> latMap = new HashMap<String, Float>();
  private HashMap<String, Float> lonMap = new HashMap<String, Float>();
  private HashMap<String, Float> altMap = new HashMap<String, Float>();

  public HashMap<String, Float> getLatMap() {
    return this.latMap;
  }

  public HashMap<String, Float> getLonMap() {
    return this.lonMap;
  }

  public HashMap<String, Float> getAltMap() {
    return this.altMap;
  }

  public boolean hasPlane(InetAddress ip) {
    return this.latMap.containsKey(ip.toString()) && lonMap.containsKey(ip.toString());
  }

  public void setPlaneLat(InetAddress ip, float lat) {
    this.latMap.put(ip.toString(), lat);
  }

  public void setPlaneLon(InetAddress ip, float lon) {
    this.lonMap.put(ip.toString(), lon);
  }

  public void setPlaneAlt(InetAddress ip, float alt) {
    this.altMap.put(ip.toString(), alt);
  }


  public float[] getPlaneCoordinates(InetAddress ip) throws Exception {
    float result[] = new float[3];
    if (this.hasPlane(ip)) {
      result[0] = this.getPlaneLat(ip);
      result[1] = this.getPlaneLon(ip);
      result[2] = this.getPlaneAlt(ip);
    }
    else throw new Exception("Plane ip " + ip.toString() + "does not exist");
    return result;

  }

  public float getPlaneLat(InetAddress ip) {
    return this.latMap.get(ip.toString());
  }

  public float getPlaneLon(InetAddress ip) {
    return this.lonMap.get(ip.toString());
  }

  public float getPlaneAlt(InetAddress ip) {
    return this.altMap.get(ip.toString());
  }

}
