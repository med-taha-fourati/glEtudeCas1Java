package com.fsegs.genie_logiciel_etude_cas_1.Scheduling;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.fsegs.genie_logiciel_etude_cas_1.Metier.Seance;
import com.fsegs.genie_logiciel_etude_cas_1.Services.SeanceService;

@Service
//TODO: develop this even further
public class SeanceScheduler {
	private SeanceService seanceService;
	
	public SeanceScheduler(SeanceService s) {
		this.seanceService = s;
	}
	
	@Scheduled(cron = "*/5 * * * * *")
	public void terminerSeancesAuto() {
		List<Seance> seances = seanceService.getAllSeances();
		seances.stream().forEach((s)->seanceService.terminerSeance(s.getId()));
	}
}
