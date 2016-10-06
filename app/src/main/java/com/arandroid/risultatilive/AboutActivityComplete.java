package com.arandroid.risultatilive;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.Utils.EntryAdapter;
import com.Utils.EntryItem;
import com.Utils.Item;
import com.Utils.SectionItem;
import com.arandroid.risultatilive.core.GlobalState;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class AboutActivityComplete extends Activity implements
		OnItemClickListener {
	/** Called when the activity is first created. */

	ArrayList<Item> items = new ArrayList<Item>();
	private static final int SITO_WEB = 3;
	private static final int FACEBOOK = 4;
	private static final int CHANGE_LOG = 6;
	private static final int MARKET_AUTORE = 8;
	private static final int SEGNALA_BUG =9;
	private ListView listView;
	private InterstitialAd interstitialAd;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		try {
			super.onCreate(savedInstanceState);
			requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
			setContentView(R.layout.aboutcomplete);
			getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
					R.layout.window_title);
			listView = (ListView) findViewById(R.id.lvab);
			PackageManager manager = this.getPackageManager();
			PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
			items.add(new SectionItem("Informazioni"));
			items.add(new EntryItem(getString(getApplicationInfo().labelRes),
					"Versione: " + info.versionName));
			items.add(new EntryItem("Autore", "ARAndroid"));
			items.add(new EntryItem("Sito Web Autore",
					"Visualizza il sito dell'autore"));
			items.add(new EntryItem("Facebook", "Visualizza la pagina Facebook"));

			items.add(new SectionItem("Informazioni Applicazione"));
			items.add(new EntryItem("ChangeLog", "Visualizza i cambiamenti"));

			items.add(new SectionItem("Varie"));
			items.add(new EntryItem("Market Autore",
					"Visualizza altre app di ARAndroid!"));
			items.add(new EntryItem("Segnala un Bug",
					"Grazie per il tuo supporto!"));

			EntryAdapter adapter = new EntryAdapter(this, items);
			listView.setOnItemClickListener(this);
			listView.setAdapter(adapter);
			
			// banners -- caricamento da ADMOB
						interstitialAd = new InterstitialAd(this);
					    interstitialAd.setAdUnitId(GlobalState.AD_UNIT_ID_INTERSTITIAL);
					    
					    // Create ad request.
					    AdRequest adRequest = new AdRequest.Builder().build();

					    // Begin loading your interstitial.
					    interstitialAd.loadAd(adRequest);
					    
					   
					    
					    // Set the AdListener.
					    interstitialAd.setAdListener(new AdListener() {
					    	public void onAdLoaded(){
					    		if (interstitialAd.isLoaded() ) {
					    	    	interstitialAd.show();
					    	    }
					          }
					    });
						
						
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onItemClick(AdapterView<?> l, View v, int position, long id) {
		if (!items.get(position).isSection()) {

			switch (position) {
			case SITO_WEB:
				Intent myIntent = new Intent(Intent.ACTION_VIEW,
						Uri.parse("http://arandroid.altervista.org"));
				startActivity(myIntent);
				break;
			case FACEBOOK:
				myIntent = new Intent(
						Intent.ACTION_VIEW,
						Uri.parse("https://www.facebook.com/pages/Arandroid/140377209441207?fref=ts"));
				startActivity(myIntent);
				break;
			case CHANGE_LOG:
				String titolo = "ChangeLog";
				String testo = "<h2>Change Log</h2><ul><li><strong>v.1.0 - 14/11/2012</strong>: </br>Primo rilascio sullo store</li><li><strong>v 1.1 - 18/09/2013</strong>: </br>Secondo rilascio sullo store: <ol><li>aggiunta di date ad ogni match</li><li>Aggiunta schermata about personalizzata</li></ol></li><li><strong>v 1.2 - 10/10/2013</strong>: </br>Terzo rilascio rilascio sullo store: <ol><li>Aggiunto campionato di seconda categoria girone a</li></ol></li></ul>";
				createCustomDialog(titolo, testo);
				break;
			case MARKET_AUTORE:
				myIntent = new Intent(
						Intent.ACTION_VIEW,
						Uri.parse("https://play.google.com/store/apps/developer?id=ARAndroid"));
				startActivity(myIntent);
				break;
			case SEGNALA_BUG:
				createDialogMail();
				break;
			default:
				break;
			}

		}

	}

	private void createDialogMail() {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		View view = LayoutInflater.from(this).inflate(R.layout.dialog,
				(RelativeLayout) findViewById(R.id.rl));
		final EditText edit = (EditText) view.findViewById(R.id.et);
		builder.setTitle("Segnala Bug");
		builder.setView(view);
		builder.setPositiveButton("Invia", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				inviamail(edit.getText().toString(), "arandroid@libero.it");
			}
		});
		AlertDialog alertDialog = builder.create();

		alertDialog.show();
	}

	private void inviamail(String string, String address) {
		Intent email = new Intent(Intent.ACTION_SEND);
		email.putExtra(Intent.EXTRA_EMAIL, new String[] { address });
		email.putExtra(Intent.EXTRA_TEXT, string);
		email.putExtra(Intent.EXTRA_SUBJECT, "Segnalazione");
		email.setType("message/rfc822");
		startActivity(Intent.createChooser(email, "Scegli il tuo Client:"));

	}

	private void createCustomDialog(String title, String testo) {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		View view = LayoutInflater.from(this).inflate(R.layout.customdialog,
				(RelativeLayout) findViewById(R.id.rl1));
		final WebView edit = (WebView) view.findViewById(R.id.wv1);
		builder.setTitle(title);
		builder.setView(view);
		builder.setPositiveButton("OK", null);
		edit.loadData(testo,"text/html", "UTF-8");
		AlertDialog alertDialog = builder.create();

		alertDialog.show();
	}

	
}