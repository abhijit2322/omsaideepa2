package abhijit.osdm2;

import java.util.ArrayList;

/**
 * Created by Oclemy for ProgrammingWizards TV Channel and https://www.camposha.info.
 */
public class CRUD {

    private ArrayList<String> names =new ArrayList<>();
    private ArrayList<String> cost =new ArrayList<>();
    private ArrayList<String> totalcost =new ArrayList<>();
    private ArrayList<String> tcost =new ArrayList<>();
   // String totalcost="";

    public void save(String name,String cost)
    {
        String total_string="For "+name+" Cost in RS = "+cost+"/-";

       names.add(total_string);
      // names.add("Totsl"+total);

    }

    public void saveTotal(String name,String cost)
    {
      //  String total_string="For "+name+" Cost in RS = "+cost+"/-";
        String total="677";
        //names.add(total_string);
        totalcost.add(total);//.add("Totsl"+total);

    }
    public ArrayList<String> getTotal()
    {
       return totalcost;

    }

    public void save_item(String name)
    {
        String total_string=name;
        tcost.add(total_string);
    }


    public void save_cost(String cost)
    {
        String total_string=cost;
        tcost.add(cost);
    }


    public ArrayList<String> getNames()
    {
        return names;
    }

    public Boolean update(int position,String newName,String cost)
    {
       try {
        //   int temp=position;
          // names.remove(position);
           String total_string="For "+newName+" Cost in RS = "+cost+"/-";
         //  names.remove(position);
           names.add(position,total_string);

           return true;
       }catch (Exception e)
       {
           e.printStackTrace();
          return false;
        }
    }

    public Boolean delete(int position)
    {
        try {
            names.remove(position);

            return true;
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;

        }
    }
}
