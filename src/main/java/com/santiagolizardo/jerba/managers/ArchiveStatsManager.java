package com.santiagolizardo.jerba.managers;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.santiagolizardo.jerba.model.ArchiveStats;

public class ArchiveStatsManager {

	private PersistenceManager pm;

	public ArchiveStatsManager(PersistenceManager pm) {
		this.pm = pm;
	}

	public ArchiveStats findByYearMonth(int year, int month) {
		ArchiveStats archiveStats = null;

		Query q = pm.newQuery(ArchiveStats.class);
		q.setFilter("year == yearParam && month == monthParam");
		q.declareParameters("Integer yearParam, Integer monthParam");
		List<ArchiveStats> stats = (List<ArchiveStats>) q.execute(year, month);
		if (stats.size() == 1) {
			archiveStats = stats.get(0);
		}
		q.closeAll();

		return archiveStats;
	}

	public List<ArchiveStats> findAll() {
		Query q = pm.newQuery(ArchiveStats.class);
		q.setOrdering("year DESC, month DESC");
		return (List<ArchiveStats>) q.execute();
	}
}
