package client;

import android.app.Activity;
import android.os.Bundle;
import java.io.DataInputStream;
import java.io.PrintStream;
import java.net.Socket;

import com.sockets.sockets_example_1.R;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

	public class MainActivity extends Activity implements OnClickListener{
	TextView text;
	Socket socket; 
	
	PrintStream out; 
	DataInputStream dt;
	
    int min=0, max=20;
    String res;
		
		@Override
		protected void onCreate(Bundle savedInstanceState) 
		{
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_main);
			text=(TextView) findViewById(R.id.text);
		    text.setOnClickListener(this);
		}
		
		public void required_number(String c){
			AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this);
			adb.setTitle("Result");
			String TextToast1 = "Your number is " + c;
			adb.setMessage(TextToast1);
			AlertDialog ad = adb.create(); ad.show();
			text.setText("Press on the inscription\nto find another values");
			max=20; min=0;
		}
		
		public void stream_creation(int a, int b)
		{
			try{
				  socket = new Socket("192.168.1.2",8888);
				  out = new PrintStream(socket.getOutputStream());
				  out.println(min); out.println(max);
				  
				  dt = new DataInputStream(socket.getInputStream());  
				  res=dt.readLine();
				  out.close(); socket.close();
				}
				catch (Exception e) {System.out.println(e);}
		}
		
		@Override
		public void onClick(View v)
	    { 	    
		  text.setText("Press on the inscription\nto find another values");
				
		  switch(v.getId())
		  { 
			case R.id.text:
				AlertDialog.Builder aDb = new AlertDialog.Builder(MainActivity.this);
				stream_creation(min, max);
				
				  String TextToast = "Is your number equal to " + res + " ?";
				  aDb.setMessage(TextToast);
				  aDb.setCancelable(false);

				
//---------------------------------------------------------------------------------------------------------------------				
				aDb.setPositiveButton("<", new DialogInterface.OnClickListener() 
				{
					@Override
					public void onClick(DialogInterface dialog,int which) 
					{
					max=Integer.parseInt(res);
									
					  if (max==1 || max<1) 
					  {
					  Toast.makeText(MainActivity.this, "Unable to decrement. The left boundary of the interval is 1", Toast.LENGTH_LONG).show();
					  min = 0; max = 20; 
					  }
					  
					stream_creation(min, max);
					}
				});
				
//---------------------------------------------------------------------------------------------------------------------					
				aDb.setNeutralButton("=", new DialogInterface.OnClickListener() 
				{
					@Override
					public void onClick(DialogInterface dialog,int which) 
					{  
						required_number(res);
				        stream_creation(min, max);
					}
				}); 
				
//---------------------------------------------------------------------------------------------------------------------					
				aDb.setNegativeButton(">", new DialogInterface.OnClickListener() 
				{
					@Override
					public void onClick(DialogInterface dialog,int which) 
					{	
					  min=Integer.parseInt(res);	
					  if (min==20) 
					  {	
					  Toast.makeText(MainActivity.this, "Unable to increment. The right boundary of the interval is 20", Toast.LENGTH_LONG).show();
					  min = 0; max = 20; 
					  }
					  
					stream_creation(min, max);
					}
				});
				AlertDialog aD = aDb.create(); aD.show();
				
			break;
			}
		}
}
