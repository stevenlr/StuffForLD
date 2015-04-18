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

	protected ContinuousParticleSpawner(float delay, ParticleSystem system) {
		super(null);
		_delay = delay;
		_system = system;
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
		_timeSinceLastSpawn = 0;
		return _system.addParticle();
	}

	@Override
	public void update(float dt) {
		_timeSinceLastSpawn += dt;
	}
}
