/*
 * Copyright (c) 2015 Steven Le Rouzic
 * See LICENSE.txt for license details
 */

package com.stevenlr.waffle.particles.spawner;

import com.stevenlr.waffle.particles.Particle;
import com.stevenlr.waffle.particles.ParticleSystem;

public class ContinuousParticleSpawner extends ParticleSpawner {

	private float _delay;
	private ParticleSystem _system;
	private float _timeSinceLastSpawn = 0;

	public ContinuousParticleSpawner(float delay) {
		super(null);
		_delay = delay;
	}

	@Override
	public boolean canSpawnParticle() {
		return _timeSinceLastSpawn >= _delay;
	}

	@Override
	public boolean isDoneSpawning() {
		return false;
	}

	@Override
	public Particle spawnParticle() {
		if (_system == null) {
			throw new RuntimeException("No particle system specified");
		}

		_timeSinceLastSpawn = 0;
		return _system.addParticle();
	}

	@Override
	public void update(float dt) {
		_timeSinceLastSpawn += dt;
	}

	@Override
	public void setParticleSystem(ParticleSystem system) {
		_system = system;
	}
}
