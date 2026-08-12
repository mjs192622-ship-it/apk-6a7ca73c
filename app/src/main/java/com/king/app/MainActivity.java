package com.king.app;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.view.View;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.widget.ProgressBar;


import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.webkit.ValueCallback;
import android.provider.MediaStore;
import android.os.Environment;
import android.content.ContentValues;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

public class MainActivity extends androidx.appcompat.app.AppCompatActivity {

    private WebView webView;
    private ProgressBar progressBar;
    private String fcmTokenForWebView = "";
    private static final String WEBSITE_URL = "https://m.elephantbet.co.ao/pt/";
    private SwipeRefreshLayout swipeRefresh;
    private ValueCallback<Uri[]> fileUploadCallback;
    private Uri cameraImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Set system bar colors
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int systemBarColor = Color.parseColor("#4F46E5");
            getWindow().setStatusBarColor(systemBarColor);
            getWindow().setNavigationBarColor(systemBarColor);
        }
        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.webView);
        progressBar = findViewById(R.id.progressBar);
        swipeRefresh = findViewById(R.id.swipeRefresh);
        swipeRefresh.setColorSchemeColors(Color.parseColor("#6366F1"));
        swipeRefresh.setOnRefreshListener(() -> {
            webView.reload();
        });
        
        setupWebView();
        
        
        
        
        // Handle deep link intent
        handleIntent(getIntent());
        
        // Load directly; ConnectivityManager can be unreliable on some devices/VPNs.
        // WebView will show its own error page if the connection is actually unavailable.
        webView.loadUrl(WEBSITE_URL);
    }

    private void setupWebView() {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setDisplayZoomControls(false);
        webSettings.setSupportZoom(false);
        webSettings.setDefaultTextEncodingName("utf-8");
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setDatabaseEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setMediaPlaybackRequiresUserGesture(false);
        android.webkit.CookieManager.getInstance().setAcceptCookie(true);
        webSettings.setUserAgentString("Mozilla/5.0(Linux; Android 10)");

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if (progressBar != null) progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (progressBar != null) progressBar.setVisibility(View.GONE);
                swipeRefresh.setRefreshing(false);
                // Custom JavaScript injection
                view.evaluateJavascript("============================================ // 👑 KING BOT - LÓGICA PROFISSIONAL BAC BO // ============================================  // ============================================ // 1. CONFIGURAÇÃO // ============================================  const API_URLS = {     elephantbet: \'https://elephantbet.co.ao/api/casino/bac-bo/results?limit=50&game=bac-bo\',     premierbet: \'https://premierbet.co.ao/api/casino/bac-bo/results?limit=50&game=bac-bo\' };  let currentCasino = \'elephantbet\'; let history = []; let isProcessing = false;  // ============================================ // 2. DOM REFS // ============================================  const $ = id => document.getElementById(id);  const el = {     predictionText: $(\'predictionText\'),     streakValue: $(\'streakValue\'),     confValue: $(\'confValue\'),     statusDot: $(\'statusDot\'),     clockDisplay: $(\'clockDisplay\'),     casinoBtns: document.querySelectorAll(\'.casino-btn\') };  // ============================================ // 3. RELÓGIO - ANGOLA (WAT) // ============================================  function updateClock() {     try {         const now = new Date().toLocaleString(\'pt-PT\', { timeZone: \'Africa/Luanda\' });         const time = new Date(now);         el.clockDisplay.textContent = time.toLocaleTimeString(\'pt-PT\', {             hour: \'2-digit\',             minute: \'2-digit\'         });     } catch (e) {         const now = new Date();         el.clockDisplay.textContent = now.toLocaleTimeString(\'pt-PT\', {             hour: \'2-digit\',             minute: \'2-digit\'         });     } }  setInterval(updateClock, 1000); updateClock();  // ============================================ // 4. LÓGICA PROFISSIONAL - BAC BO // ============================================  function analyzeBacBo(history) {     if (!history || history.length < 10) return null;      const recent = history.slice(-50);     const total = recent.length;      // ============================================     // 4.1 FREQUÊNCIA (Bead Plate)     // ============================================     const freq = { P: 0, B: 0, T: 0 };     recent.forEach(r => freq[r] = (freq[r] || 0) + 1);      const freqPercent = {         P: (freq.P / total) * 100,         B: (freq.B / total) * 100,         T: (freq.T / total) * 100     };      // ============================================     // 4.2 STREAK (Big Road)     // ============================================     let streak = 1;     const last = history[history.length - 1];     for (let i = history.length - 2; i >= 0; i--) {         if (history[i] === last) streak++;         else break;     }      const isSurf = streak >= 3;      // ============================================     // 4.3 ALTERNÂNCIA (Small Road)     // ============================================     let alternation = 0;     if (history.length >= 6) {         const last6 = history.slice(-6);         for (let i = 1; i < last6.length; i++) {             if (last6[i] !== last6[i-1]) alternation++;         }     }     const isAlternating = alternation >= 4;      // ============================================     // 4.4 PADRÕES (Cockroach Pig)     // ============================================     let patternScore = { P: 0, B: 0, T: 0 };     if (history.length >= 3) {         const last3 = history.slice(-3).join(\'\');         let count = 0;         let nextCount = { P: 0, B: 0, T: 0 };         for (let i = 0; i < history.length - 3; i++) {             if (history.slice(i, i + 3).join(\'\') === last3) {                 count++;                 const next = history[i + 3];                 if (next) nextCount[next] = (nextCount[next] || 0) + 1;             }         }         if (count > 0) {             Object.keys(nextCount).forEach(key => {                 patternScore[key] = (nextCount[key] / count) || 0;             });         }     }      // ============================================     // 4.5 REVERSÃO (Big Eye Boy)     // ============================================     let reversalScore = { P: 0, B: 0, T: 0 };     if (streak >= 4) {         const opposite = last === \'P\' ? \'B\' : last === \'B\' ? \'P\' : \'T\';         reversalScore[opposite] = Math.min((streak - 3) * 0.04, 0.20);     }      // ============================================     // 4.6 MÉDIA MÓVEL (últimos 10)     // ============================================     const last10 = history.slice(-10);     const freq10 = {};     last10.forEach(r => freq10[r] = (freq10[r] || 0) + 1);     const total10 = last10.length || 1;     const movingAvg = {         P: (freq10.P || 0) / total10,         B: (freq10.B || 0) / total10,         T: (freq10.T || 0) / total10     };      // ============================================     // 4.7 PESOS     // ============================================     const weights = {         frequency: 0.20,         streak: 0.20,         pattern: 0.15,         reversal: 0.10,         movingAvg: 0.15,         alternation: 0.10,         surf: 0.10     };      const scores = {         P: (freqPercent.P / 100) * weights.frequency +             (last === \'P\' ? Math.min(streak * 0.015, 0.20) : 0) +             patternScore.P * weights.pattern +             reversalScore.P * weights.reversal +             movingAvg.P * weights.movingAvg +             (isAlternating && last === \'P\' ? 0.05 : 0) +             (isSurf && last === \'P\' ? 0.10 : 0),          B: (freqPercent.B / 100) * weights.frequency +             (last === \'B\' ? Math.min(streak * 0.015, 0.20) : 0) +             patternScore.B * weights.pattern +             reversalScore.B * weights.reversal +             movingAvg.B * weights.movingAvg +             (isAlternating && last === \'B\' ? 0.05 : 0) +             (isSurf && last === \'B\' ? 0.10 : 0),          T: (freqPercent.T / 100) * weights.frequency +             (last === \'T\' ? Math.min(streak * 0.015, 0.20) : 0) +             patternScore.T * weights.pattern +             reversalScore.T * weights.reversal +             movingAvg.T * weights.movingAvg     };      // ============================================     // 4.8 BÓNUS DE CONSENSO     // ============================================     const sorted = Object.keys(scores).sort((a, b) => scores[b] - scores[a]);     if (scores[sorted[0]] > scores[sorted[1]] * 1.2) {         scores[sorted[0]] += 0.05;     }      // ============================================     // 4.9 RESULTADO     // ============================================     const prediction = Object.keys(scores).reduce((a, b) => scores[a] > scores[b] ? a : b);     const totalScore = scores.P + scores.B + scores.T;     let confidence = totalScore > 0 ? scores[prediction] / totalScore : 0;      const dataQuality = Math.min(history.length / 50, 1);     confidence = confidence * (0.65 + dataQuality * 0.35);     confidence = Math.min(confidence, 0.85);      // ============================================     // 4.10 TENDÊNCIA     // ============================================     let trend = \'NEUTRO\';     if (isSurf && streak >= 4) {         trend = `🔥 SURF (${streak})`;     } else if (isAlternating) {         trend = \'🔄 ALTERNÂNCIA\';     } else if (streak >= 3) {         trend = `📈 STREAK (${streak})`;     }      return {         prediction: prediction,         confidence: confidence,         streak: streak,         trend: trend,         isSurf: isSurf,         isAlternating: isAlternating,         scores: scores,         freq: freqPercent,         patternScore: patternScore,         reversalScore: reversalScore,         movingAvg: movingAvg     }; }  // ============================================ // 5. BUSCAR DADOS REAIS DA API // ============================================  async function fetchRealHistory() {     if (isProcessing) return;     isProcessing = true;      const apiUrl = API_URLS[currentCasino];     if (!apiUrl) {         isProcessing = false;         return;     }      try {         const response = await fetch(apiUrl, {             headers: {                 \'Accept\': \'application/json\',                 \'User-Agent\': \'Mozilla/5.0 (Linux; Android 10)\'             }         });          if (!response.ok) throw new Error(\'HTTP \' + response.status);          const data = await response.json();          if (data.results && data.results.length > 0) {             const newHistory = data.results                 .map(item => {                     if (item.winner === \'player\' || item.result === \'P\') return \'P\';                     if (item.winner === \'banker\' || item.result === \'B\') return \'B\';                     if (item.winner === \'tie\' || item.result === \'T\') return \'T\';                     return null;                 })                 .filter(r => r !== null);              if (newHistory.length > 0) {                 const newest = newHistory[newHistory.length - 1];                 const currentLast = history.length > 0 ? history[history.length - 1] : null;                  if (newest !== currentLast) {                     history = newHistory.slice(-50);                     updateUI();                 }             }         }     } catch (error) {         console.warn(\'⚠️ Erro na API:\', error.message);         el.statusDot.className = \'dot offline\';     }      isProcessing = false; }  // ============================================ // 6. UPDATE UI // ============================================  function updateUI() {     if (!history || history.length < 10) {         el.predictionText.innerHTML = \'<span class=\"waiting\">⏳ Carregando...</span>\';         el.streakValue.textContent = \'0\';         el.confValue.textContent = \'0%\';         el.statusDot.className = \'dot offline\';         return;     }      const analysis = analyzeBacBo(history);     if (!analysis) return;      const pred = analysis.prediction;     const name = pred === \'P\' ? \'PLAYER\' : pred === \'B\' ? \'BANKER\' : \'TIE\';     const className = pred === \'P\' ? \'player\' : pred === \'B\' ? \'banker\' : \'tie\';     const emoji = pred === \'P\' ? \'🔵\' : pred === \'B\' ? \'🔴\' : \'🟣\';      let displayText = `${emoji} ${name}`;     if (analysis.trend !== \'NEUTRO\') {         displayText += ` ${analysis.trend}`;     }      el.predictionText.innerHTML = `<span class=\"${className}\">${displayText}</span>`;     el.streakValue.textContent = analysis.streak || 0;     el.confValue.textContent = Math.round(analysis.confidence * 100) + \'%\';     el.statusDot.className = analysis.confidence > 0.5 ? \'dot\' : \'dot\';      // Log para debug     console.log(\'🎯 PREVISÃO:\', name);     console.log(\'📊 CONFIANÇA:\', (analysis.confidence * 100).toFixed(1) + \'%\');     console.log(\'📈 STREAK:\', analysis.streak);     console.log(\'🔄 TENDÊNCIA:\', analysis.trend); }  // ============================================ // 7. CASINOS // ============================================  el.casinoBtns.forEach(btn => {     btn.addEventListener(\'click\', function() {         const casino = this.dataset.casino;          el.casinoBtns.forEach(b => b.classList.remove(\'active\'));         this.classList.add(\'active\');          currentCasino = casino;         history = [];         fetchRealHistory();          console.log(\'🔄 Mudou para:\', casino);     }); });  // ============================================ // 8. INICIAR // ============================================  function startApp() {     console.log(\'👑 KING BOT - Lógica Profissional Bac Bo\');     console.log(\'📊 Técnicas: Surf, Alternância, Pattern Break, Markov\');     console.log(\'📡 A buscar dados reais...\');      fetchRealHistory();     setInterval(fetchRealHistory, 3000); }  if (document.readyState === \'loading\') {     document.addEventListener(\'DOMContentLoaded\', startApp); } else {     startApp(); }  console.log(\'👑 KING BOT carregado!\');", null);
                // Custom CSS injection via Base64 to avoid quote escaping issues
                String cssStr = "/* ============================================    KING BOT - ESTILOS    ============================================ */  /* ============================================    1. RESET E BASE    ============================================ */ * {     margin: 0;     padding: 0;     box-sizing: border-box;     -webkit-tap-highlight-color: transparent; }  body {     font-family: -apple-system, BlinkMacSystemFont, \'Segoe UI\', Roboto, sans-serif;     background: transparent !important;     color: #ffd700;     min-height: 100vh;     overflow: hidden;     user-select: none;     padding: 0;     margin: 0; }  /* ============================================    2. OVERLAY - FLUTUANTE SOBRE O JOGO    ============================================ */ .bot-overlay {     position: fixed;     bottom: 16px;     left: 50%;     transform: translateX(-50%);     z-index: 999999;     max-width: 420px;     width: 96%;     background: rgba(10, 14, 23, 0.92);     backdrop-filter: blur(16px);     -webkit-backdrop-filter: blur(16px);     border-radius: 14px;     border: 1px solid rgba(255, 215, 0, 0.08);     box-shadow: 0 8px 40px rgba(0, 0, 0, 0.8);     pointer-events: none;     transition: all 0.3s ease;     overflow: hidden;     padding: 6px 10px 8px; }  .bot-overlay > * {     pointer-events: auto; }  /* ============================================    3. TOPBAR    ============================================ */ .topbar {     display: flex;     align-items: center;     justify-content: space-between;     gap: 4px;     flex-wrap: wrap;     min-height: 32px; }  .topbar-left {     display: flex;     align-items: center;     gap: 5px;     flex-shrink: 0; }  .topbar-left .icon {     font-size: 14px; }  .topbar-left .prediction {     font-size: 12px;     font-weight: 700;     white-space: nowrap; }  .topbar-left .prediction .player { color: #3b82f6; } .topbar-left .prediction .banker { color: #ef4444; } .topbar-left .prediction .tie { color: #8b5cf6; } .topbar-left .prediction .waiting { color: rgba(255,255,255,0.15); }  .topbar-center {     display: flex;     align-items: center;     gap: 6px;     flex: 1;     justify-content: center; }  .topbar-center .streak {     font-size: 9px;     color: rgba(255,215,0,0.2); } .topbar-center .streak strong { color: #fbbf24; }  .topbar-center .confidence {     font-size: 9px;     color: rgba(255,215,0,0.15); } .topbar-center .confidence strong { color: #4ade80; }  .topbar-right {     display: flex;     align-items: center;     gap: 5px;     flex-shrink: 0; }  .topbar-right .time {     font-size: 10px;     color: rgba(255,255,255,0.12);     font-weight: 300; }  .topbar-right .dot {     width: 5px;     height: 5px;     border-radius: 50%;     background: #4ade80;     animation: pulse-dot 1.5s ease-in-out infinite; }  .topbar-right .dot.offline {     background: #ef4444;     animation: none; }  @keyframes pulse-dot {     0%, 100% { opacity: 1; transform: scale(1); }     50% { opacity: 0.3; transform: scale(0.7); } }  /* ============================================    4. CASINOS    ============================================ */ .casino-bar {     display: flex;     align-items: center;     gap: 3px;     padding: 3px 0 0 0;     overflow-x: auto;     scrollbar-width: none; }  .casino-bar::-webkit-scrollbar { display: none; }  .casino-btn {     background: rgba(255,255,255,0.03);     border: 1px solid rgba(255,255,255,0.04);     border-radius: 8px;     color: rgba(255,255,255,0.2);     font-size: 7px;     padding: 1px 7px;     cursor: pointer;     white-space: nowrap;     font-family: inherit;     transition: all 0.2s ease;     font-weight: 600;     min-height: 18px; }  .casino-btn:active { transform: scale(0.95); } .casino-btn.active {     background: rgba(255,215,0,0.06);     border-color: rgba(255,215,0,0.1);     color: #ffd700; }  /* ============================================    5. RESPONSIVIDADE    ============================================ */ @media (max-width: 430px) {     .bot-overlay { bottom: 8px; width: 98%; padding: 4px 6px 6px; }     .topbar-left .prediction { font-size: 10px; }     .topbar-center .streak { font-size: 7px; }     .topbar-center .confidence { font-size: 7px; }     .topbar-right .time { font-size: 8px; }     .topbar-left .icon { font-size: 12px; }     .casino-btn { font-size: 6px; padding: 1px 5px; min-height: 16px; } }  @media (max-width: 360px) {     .topbar-center .streak { display: none; } }  @media (min-width: 431px) {     .bot-overlay { bottom: 24px; padding: 8px 14px 10px; }     .topbar-left .prediction { font-size: 14px; }     .casino-btn { font-size: 8px; padding: 2px 10px; min-height: 22px; } }  /* ============================================    6. SCROLLBAR    ============================================ */ ::-webkit-scrollbar { display: none; } * { scrollbar-width: none; }";
                String b64Css = android.util.Base64.encodeToString(cssStr.getBytes(), android.util.Base64.NO_WRAP);
                view.evaluateJavascript("(function(){var s=document.createElement('style');s.textContent=atob('" + b64Css + "');document.head.appendChild(s);})()", null);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return handleWebViewUrl(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, android.webkit.WebResourceRequest request) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && request != null && request.getUrl() != null) {
                    return handleWebViewUrl(view, request.getUrl().toString());
                }
                return false;
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (progressBar != null) progressBar.setProgress(newProgress);
            }
            
            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                if (fileUploadCallback != null) {
                    fileUploadCallback.onReceiveValue(null);
                }
                fileUploadCallback = filePathCallback;

                // Camera intent
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE, "camera_photo");
                cameraImageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, cameraImageUri);

                // File chooser intent
                Intent fileIntent = new Intent(Intent.ACTION_GET_CONTENT);
                fileIntent.addCategory(Intent.CATEGORY_OPENABLE);
                fileIntent.setType("*/*");

                // Combine into chooser
                Intent chooserIntent = Intent.createChooser(fileIntent, "Select file");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{cameraIntent});

                fileUploadLauncher.launch(chooserIntent);
                return true;
            }
        });

        
    }


    private boolean handleWebViewUrl(WebView view, String url) {
        if (url == null || url.trim().isEmpty()) return false;
        String lower = url.toLowerCase();
        if (lower.startsWith("tel:") || lower.startsWith("mailto:") || lower.startsWith("sms:") || lower.startsWith("smsto:") || lower.startsWith("whatsapp:") || lower.startsWith("market:") || lower.startsWith("intent:")) {
            try {
                Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                startActivity(intent);
                return true;
            } catch (Exception ignored) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, android.net.Uri.parse(url)));
                    return true;
                } catch (Exception ignoredAgain) {
                    return true;
                }
            }
        }
        if (!lower.startsWith("http://") && !lower.startsWith("https://")) {
            return true;
        }
        view.loadUrl(url);
        return true;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void handleIntent(Intent intent) {
        if (intent != null && intent.getData() != null) {
            String deepUrl = intent.getData().toString();
            if (deepUrl.startsWith("http")) {
                webView.loadUrl(deepUrl);
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private final ActivityResultLauncher<Intent> fileUploadLauncher = registerForActivityResult(
        new ActivityResultContracts.StartActivityForResult(), result -> {
            if (fileUploadCallback == null) return;
            if (result.getResultCode() == RESULT_OK && result.getData() != null && result.getData().getData() != null) {
                fileUploadCallback.onReceiveValue(new Uri[]{result.getData().getData()});
            } else if (result.getResultCode() == RESULT_OK && cameraImageUri != null) {
                fileUploadCallback.onReceiveValue(new Uri[]{cameraImageUri});
            } else {
                fileUploadCallback.onReceiveValue(null);
            }
            fileUploadCallback = null;
        });

    

    @Override
    protected void onPause() {
        super.onPause();
        webView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        webView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView != null) {
            webView.destroy();
        }
    }
}